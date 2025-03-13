package model.APIHandler.input;

import model.APIHandler.HTTPHelper.HTTPHandler;
import model.marvelObjects.Character;
import model.marvelObjects.DisplayComic;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

public class OnlineInputHandler implements InputHandler{
    private final String publicAPIKey;
    private final String ts;
    private final String hash;

    public OnlineInputHandler(String publicAPIKey, String privateAPIKey){
        this.publicAPIKey = publicAPIKey;
        this.ts = String.valueOf(System.currentTimeMillis());
        this.hash = DigestUtils.md5Hex(this.ts + privateAPIKey + publicAPIKey);
    }

    @Override
    public List<Character> getCharacterList(String searchTerm) {
        String nameStartsWith = "nameStartsWith=" + searchTerm.replace(" ", "%20") + "&";
        if (searchTerm == "") {
            nameStartsWith = "";
        }

        String request = "https://gateway.marvel.com/v1/public/characters?"+nameStartsWith + "ts="+ts + "&apikey="+publicAPIKey +"&hash="+hash;

        try{
            HttpResponse<String> statusRequest = HTTPHandler.getRequest(request);

            ArrayList<Character> characters = new ArrayList<>();
            JSONObject obj = new JSONObject(statusRequest.body());
            obj = obj.getJSONObject("data");

            JSONArray a = obj.getJSONArray("results");
            for (int i = 0; i < a.length(); i++){
                characters.add(new Character(a.getJSONObject(i)));
            }
            return characters;
        } catch (Exception e){
            ;
        }
        return new ArrayList<Character>();
    }

    @Override
    public List<DisplayComic> getComicsOfCharacter(int characterID) {
        String request = "https://gateway.marvel.com:443/v1/public/characters/" + characterID + "/comics?" + "ts="+ts + "&apikey="+publicAPIKey +"&hash="+hash;

        try {
            HttpResponse<String> statusRequest = HTTPHandler.getRequest(request);
            ArrayList<DisplayComic> comics = new ArrayList<>();
            JSONObject obj = new JSONObject(statusRequest.body());
            obj = obj.getJSONObject("data");
            JSONArray a = obj.getJSONArray("results");
            for (int i = 0; i < a.length(); i++) {
                JSONObject o = a.getJSONObject(i);
                JSONObject o2 = o.getJSONObject("characters");
                JSONArray charObject = o2.getJSONArray("items");
                List<String> characters = new ArrayList<>();
                for (int j = 0; j < charObject.length(); j++){
                    characters.add(((JSONObject)charObject.get(j)).getString("name"));
                }
                comics.add(new DisplayComic(o.getInt("id"), o.getString("title"), characters));
            }
            return comics;
        } catch (Exception e){
            ;
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCharactersInComic(int comicID) {
        String request = "https://gateway.marvel.com:443/v1/public/comics/" +comicID+ "/characters?"+ "ts="+ts + "&apikey="+publicAPIKey +"&hash="+hash;
        try{
            HttpResponse<String> statusRequest = HTTPHandler.getRequest(request);
            ArrayList<String> characters = new ArrayList<>();
            JSONObject obj = new JSONObject(statusRequest.body());
            obj = obj.getJSONObject("data");
            JSONArray a = obj.getJSONArray("results");
            for (int i = 0; i < a.length(); i++){
                JSONObject o = a.getJSONObject(i);
                characters.add(o.getString("name"));
            }
            return characters;

        } catch (Exception e){
            ;
        }


        return new ArrayList<>();
    }

    @Override
    public Character getCharacterWithName(String name) {
        String request = "https://gateway.marvel.com:443/v1/public/characters?name="+name.replace(" ", "%20")+"&"+ "ts="+ts + "&apikey="+publicAPIKey +"&hash="+hash;
        try{
            HttpResponse<String> statusRequest = HTTPHandler.getRequest(request);
            JSONObject obj = new JSONObject(statusRequest.body());
            obj = obj.getJSONObject("data");
            JSONArray a = obj.getJSONArray("results");
            JSONObject o = a.getJSONObject(0);
            return new Character(o);
        } catch (Exception e){
            ;
        }

        return null;
    }
}
