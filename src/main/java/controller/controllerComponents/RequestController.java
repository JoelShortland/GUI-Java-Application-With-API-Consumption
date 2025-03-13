package controller.controllerComponents;

import controller.AppController;
import javafx.application.Platform;
import model.AppModel;
import model.history.HistoryEntry;
import model.marvelObjects.Character;
import model.marvelObjects.DisplayComic;
import view.AppView;
import view.CacheResponse;
import view.display.CharacterSearchDisplay;
import view.display.IndividualCharacterDisplay;
import view.display.IndividualComicDisplay;

import java.util.List;

/**
 * A part of AppController that deals with HTTP requests
 */
public class RequestController {
    private AppController controller;
    private AppModel model;
    private AppView view;

    public RequestController(AppController controller, AppModel model, AppView view){
        this.controller = controller;
        this.model = model;
        this.view = view;
    }

    /**
     * If we use the searchbar, process the request in a new thread and update view once the request is done
     * @param view
     * @param searchTerm the term entered into the search bar
     */
    public void search(AppView view, String searchTerm){
        //A search is being conducted
        controller.getMenuController().alertLoadingBar(1);

        //Create a new thread to run the request
        Thread thread = new Thread(() -> {
            List<Character> characters = getCharacterList(searchTerm);
            CharacterSearchDisplay searchResultsDisplay = new CharacterSearchDisplay(characters, view);
            Platform.runLater(() -> {
                controller.getMenuController().alertLoadingBar(-1);
                searchResultsDisplay.display();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Used when a character is selected from the search results, gets the character info in a new thread and updates view after
     * @param selectedCharacter the character selected by the user
     */
    public void selectCharacter(Character selectedCharacter){
        if (selectedCharacter == null) {
            view.createFadingText(440, 90, "Please select a character", 3000);
            return;
        }

        //Run the request in a new thread
        controller.getMenuController().alertLoadingBar(1);
        Thread thread = new Thread(() -> {
            IndividualCharacterDisplay individualCharacterDisplay = new IndividualCharacterDisplay(selectedCharacter, view);
            model.getHistoryHandler().addToHistory(new HistoryEntry(selectedCharacter.getName(), individualCharacterDisplay));
            view.updateHistoryBox();
            Platform.runLater(() -> {
                controller.getMenuController().alertLoadingBar(-1);
                individualCharacterDisplay.display();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Select a comic belonging to a character, run any http requests in a new thread
     * @param selectedComic the comic selected
     */
    public void selectComic(DisplayComic selectedComic){
        if (selectedComic == null) {
            view.createFadingText(580, 125, "Please select a comic", 3000);
            return;
        }

        //Create a thread to run operations
        controller.getMenuController().alertLoadingBar(1);
        Thread thread = new Thread(() -> {
            IndividualComicDisplay individualComicDisplay = new IndividualComicDisplay(selectedComic, view);
            model.getHistoryHandler().addToHistory(new HistoryEntry(selectedComic.getName(), individualComicDisplay));
            Platform.runLater(() -> {
                view.updateHistoryBox();
                controller.getMenuController().alertLoadingBar(-1);
                individualComicDisplay.display();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Select a character from a comic list
     * @param selectedCharacter the character selected by the user
     */
    public void selectCharacter(String selectedCharacter){
        if (selectedCharacter == null) {
            view.createFadingText(330, 120, "Please select a character", 3000);
            return;
        }

        //Check if we can perform the operation without an API call
        controller.getMenuController().alertLoadingBar(1);
        if (model.getCacheHandler().isCharacterCached(selectedCharacter)){
            //if we are simply using cached data
            CacheResponse response = controller.getView().createCacheAlert();
            if (response == CacheResponse.USE_CACHE){
                Character c = model.getCacheHandler().getCachedCharacter(selectedCharacter);
                IndividualCharacterDisplay individualCharacterDisplay = new IndividualCharacterDisplay(c, view);
                view.getController().getModel().getHistoryHandler().addToHistory(new HistoryEntry(selectedCharacter, individualCharacterDisplay));
                view.updateHistoryBox();
                controller.getMenuController().alertLoadingBar(-1);
                individualCharacterDisplay.display();
                return;
            }
        }

        //Use concurrency for an actual API call
        Thread thread = new Thread(() -> {
            Character c = getCharacterWithName(selectedCharacter);
            IndividualCharacterDisplay individualCharacterDisplay = new IndividualCharacterDisplay(c, view);
            view.getController().getModel().getHistoryHandler().addToHistory(new HistoryEntry(selectedCharacter, individualCharacterDisplay));
            Platform.runLater(() -> {
                view.updateHistoryBox();
                controller.getMenuController().alertLoadingBar(-1);
                individualCharacterDisplay.display();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    public List<Character> getCharacterList(String searchTerm) {
        return model.getCharacterList(searchTerm);
    }

    public List<DisplayComic> getComicsOfCharacter(int characterID) {
        return model.getComicsOfCharacter(characterID);
    }

    public Character getCharacterWithName(String name) {
        return model.getCharacterWithName(name);
    }

    /**
     * Send a message to twilio in a new thread
     * @param he the programs history
     */
    public void sendMessage(List<HistoryEntry> he){
        controller.getMenuController().alertLoadingBar(1);
        Thread thread = new Thread(() -> {
            boolean b = model.getOutputHandler().sendMessage(he);
            Platform.runLater(() -> {
                view.createFadingText(720, 145, model.getTwilioValidityString(b), 3000);
                controller.getMenuController().alertLoadingBar(-1);
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Send a message to reddit in a new thread
     */
    public void sendRedditMessage(){
        controller.getMenuController().alertLoadingBar(1);
        Thread thread = new Thread(() -> {
            boolean b = model.getOutputHandler().postToReddit(model.getHistoryHandler().getHistory());
            Platform.runLater(() -> {
                view.createFadingText(725, 625, model.getRedditValidityString(b), 3000);
                controller.getMenuController().alertLoadingBar(-1);
            });
        });
        thread.setDaemon(true);
        thread.start();
    }
}