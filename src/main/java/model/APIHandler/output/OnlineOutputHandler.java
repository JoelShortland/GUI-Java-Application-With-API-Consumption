package model.APIHandler.output;

import model.history.HistoryEntry;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class OnlineOutputHandler implements OutputHandler{
    //Twilio
    private String key;
    private String sid;
    private String fromNumber;
    private String toNumber;

    //Reddit
    private String username;
    private String password;
    private String secret;
    private String clientID;

    public OnlineOutputHandler(String key, String sid, String fromNumber, String toNumber, String clientID, String secret){
        this.key = key;
        this.sid = sid;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.clientID = clientID;
        this.secret = secret;
    }

    @Override
    public boolean sendMessage(List<HistoryEntry> history) {
        try{
            //Set http link
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://api.twilio.com/2010-04-01/Accounts/"+ sid +"/Messages.json?");

            //Set params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To", toNumber));
            params.add(new BasicNameValuePair("From", fromNumber));
            params.add(new BasicNameValuePair("Body", createMessage(history)));
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            //set auth header
            String auth = "Basic " + Base64.getEncoder().encodeToString((sid + ":" + key).getBytes());
            httpPost.setHeader("Authorization", auth);

            //make request
            CloseableHttpResponse response = client.execute(httpPost);
            JSONObject o = new JSONObject(EntityUtils.toString(response.getEntity()));
            client.close();

            //Return based on success
            if (o.getString("status").equals("queued")){
                return true;
            }
        } catch (Exception e){
            ;
        }
        return false;
    }

    @Override
    public boolean postToReddit(List<HistoryEntry> history){
        //Dont bother with the request if it will fail
        if (username == null || password == null || secret == null || clientID == null){
            return false;
        }
        //Do the request
        String token = getRedditAuthToken(username, password, clientID, secret);
        return makeRedditPost(token, "My Marvel Application History", createMessage(history), username);
    }

    @Override
    public void setRedditUsernameAndPassword(String username, String password) {
        if (username != null) { this.username = username; }
        if (password != null) {this.password = password;}
    }

    /**
     * Get the auth token corrisponding to the current data
     * @param username Provided username
     * @param password Provided password
     * @param clientID the current clients ID
     * @param secret The current reddit secret
     * @return the auth token
     */
    private String getRedditAuthToken(String username, String password, String clientID, String secret){
        //Login with Oauth
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://www.reddit.com/api/v1/access_token.json?");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("grant_type", "password"));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            String auth = "Basic " + Base64.getEncoder().encodeToString((clientID + ":" + secret).getBytes());
            httpPost.setHeader("Authorization", auth);
            httpPost.setHeader("User-Agent", "ChangeMeClient/0.1 by YourUsername");
            CloseableHttpResponse response = client.execute(httpPost);
            JSONObject o = new JSONObject(EntityUtils.toString(response.getEntity()));
            client.close();
            return o.getString("access_token");

        } catch (Exception e){

        }
        return null;
    }

    /**
     * Try sending the post to reddit
     * @param authToken auth token
     * @param title title of reddit post
     * @param body the body of the reddit post (history of program)
     * @param username the users username
     * @return true/false depending on success
     */
    public boolean makeRedditPost(String authToken, String title, String body, String username){
        try{
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://oauth.reddit.com/api/submit.json?");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("title", title));
            params.add(new BasicNameValuePair("text", body));
            params.add(new BasicNameValuePair("sr", "u_" + username));
            params.add(new BasicNameValuePair("kind", "self"));

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            String auth = "bearer " + authToken;
            httpPost.setHeader("Authorization", auth);
            httpPost.setHeader("User-Agent", "ChangeMeClient/0.1 by YourUsername");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            CloseableHttpResponse response = client.execute(httpPost);
            JSONObject o = new JSONObject(EntityUtils.toString(response.getEntity()));
            client.close();
            if (o.getBoolean("success")){
                return true;
            }
        } catch (Exception e){

        }
        return false;
    }

    /**
     * Creates a message based on application history
     * @param history history of the app
     * @return A message covering the history
     */
    private String createMessage(List<HistoryEntry> history){
        if (history.size() == 0){
            return "Your Marvel Application's History is Empty";
        }

        String historyString = "You have recently searched for: ";
        for (int i = 0; i < history.size(); i++){
            historyString += history.get(i).getName();
            if (i < history.size()-1){ historyString += ", ";}
        }
        return  historyString;
    }
}
