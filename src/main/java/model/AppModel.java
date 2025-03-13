package model;

import controller.AppController;
import model.APIHandler.input.InputHandler;
import model.APIHandler.input.OfflineInputHandler;
import model.APIHandler.input.OnlineInputHandler;
import model.APIHandler.output.OfflineOutputHandler;
import model.APIHandler.output.OnlineOutputHandler;
import model.APIHandler.output.OutputHandler;
import model.cacheHandler.CacheHandler;
import model.history.HistoryHandler;
import model.marvelObjects.Character;
import model.marvelObjects.DisplayComic;
import view.CacheResponse;

import java.util.List;

/**
 * The primary model class
 */
public class AppModel {
    private InputHandler inputHandler;
    private OutputHandler outputHandler;
    private HistoryHandler historyHandler=new HistoryHandler();
    private PinnedHandler pinnedHandler = new PinnedHandler();
    private CacheHandler cacheHandler;
    private AppController controller;

    private int operationsPerformed=0;
    private String redditClientID;
    private String redditSecret;

    public AppModel(String inputState, String outputState){
        //Define input handler
        if ("online".equalsIgnoreCase(inputState)){
            this.inputHandler = new OnlineInputHandler(System.getenv("INPUT_API_KEY"), System.getenv("INPUT_API_APP_ID"));
            this.cacheHandler = new CacheHandler(true);
        } else{
            this.inputHandler = new OfflineInputHandler();
            this.cacheHandler = new CacheHandler(false);
        }

        //Define reddit keys (optional)
        try{
            redditClientID = System.getenv("REDDIT_CLIENT_ID");
        } catch (Exception e){
            redditClientID = null;
        }
        try{
            redditSecret = System.getenv("REDDIT_SECRET");
        } catch (Exception e){
            redditSecret = null;
        }

        //Create output handler
        if ("online".equalsIgnoreCase(outputState)){
            this.outputHandler = new OnlineOutputHandler(System.getenv("TWILIO_API_KEY"), System.getenv("TWILIO_API_SID"), System.getenv("TWILIO_API_FROM"), System.getenv("TWILIO_API_TO"), redditClientID, redditSecret);
        } else{
            this.outputHandler = new OfflineOutputHandler();
        }
    }

    public PinnedHandler getPinnedHandler(){return this.pinnedHandler; }

    public CacheHandler getCacheHandler(){
        return cacheHandler;
    }

    public void setController(AppController controller){
        this.controller = controller;
    }

    public HistoryHandler getHistoryHandler(){return this.historyHandler;}

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    /**
     * Called whenever an operation is performed or ended
     * @param addOrSubtract the number of operations being started(+ve) or stopped(-ve)
     * @return true if at least 1 operation is running, else false
     */
    public boolean operationIsBeingPerformed(int addOrSubtract){
        this.operationsPerformed += addOrSubtract;
        if (operationsPerformed == 0){
            return false;
        } else{
            return true;
        }
    }

    /**
     * Check if the user has set reddit keys
     * @return true if valid, else false
     */
    public boolean areRedditKeysValid(){
        if (redditSecret != null && redditClientID != null){
            return true;
        }
        return false;
    }

    /**
     * Calls the inputhandler and gets characters corrisponding to the search term, and tries to cache the results
     * @param searchTerm the search term entered by the user
     * @return the corrisponding characters
     */
    public List<Character> getCharacterList(String searchTerm) {
        List<Character> characters = inputHandler.getCharacterList(searchTerm);
        if (cacheHandler.isCacheEnabled()){
            for (Character c : characters){
                cacheHandler.cacheCharacter(c);
            }
        }
        return characters;
    }

    public List<DisplayComic> getComicsOfCharacter(int characterID) {
        return inputHandler.getComicsOfCharacter(characterID);
    }

    /**
     * Gets the characters in a comic, if cached lets us know and returns the corrisponding characters
     * @param comicID the ID of the comic
     * @return the corrisponding characters
     */
    public List<String> getCharactersInComic(int comicID) {
        if (cacheHandler.isComicCached(comicID)){
            CacheResponse response = controller.getView().createCacheAlert();
            if (response == CacheResponse.USE_CACHE){
                return cacheHandler.getCharactersInComic(comicID);
            }
        }
        List<String> s = inputHandler.getCharactersInComic(comicID);
        cacheHandler.cacheCharacterList(s, comicID);
        return s;
    }

    public Character getCharacterWithName(String name) {
        Character c = inputHandler.getCharacterWithName(name);
        cacheHandler.cacheCharacter(c);
        return c;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }

    public void clearCache(){
        cacheHandler.clearCache();
    }


    public String getRedditValidityString(boolean b){
        if (b){
            return "Posted to Reddit successfully";
        }
        return "Failed to post to Reddit";
    }

    public String getTwilioValidityString(boolean b){
        if (b){
            return "Sent Twilio message successfully";
        }
        return "Failed to send message to Twilio";
    }
}
