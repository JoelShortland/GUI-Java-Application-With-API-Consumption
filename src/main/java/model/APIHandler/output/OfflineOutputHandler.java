package model.APIHandler.output;

import model.history.HistoryEntry;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.List;

public class OfflineOutputHandler implements OutputHandler{
    @Override
    public boolean sendMessage(List<HistoryEntry> history) {
        return false;
    }

    @Override
    public boolean postToReddit(List<HistoryEntry> history){
        return false;
    }

    @Override
    public void setRedditUsernameAndPassword(String username, String password) {
        return;
    }
}
