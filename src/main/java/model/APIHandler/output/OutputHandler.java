package model.APIHandler.output;

import model.history.HistoryEntry;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.List;

public interface OutputHandler {
    /**
     * Send a message to Twilio provided the history
     * @param history the history of the program
     * @return true/false depending on the success
     */
    public boolean sendMessage(List<HistoryEntry> history);

    /**
     * Send a message to Reddit provided the history
     * @param history the program history
     * @return true/false depending on the success
     */
    public boolean postToReddit(List<HistoryEntry> history);

    /**
     * Allow us to configure the redditors username and password
     * @param username
     * @param password
     */
    public void setRedditUsernameAndPassword(String username, String password);
}
