package controller.controllerComponents;

import controller.AppController;
import model.AppModel;
import view.AppView;

/**
 * Handles the menu items not present in the other controllers
 */
public class MenuController {
    private AppController controller;
    private AppModel model;
    private AppView view;

    public MenuController(AppController controller, AppModel model, AppView view){
        this.controller = controller;
        this.model = model;
        this.view = view;
    }

    /**
     * If a request is made, let the model know
     * @param addOrSubtract +1 if a request is started, -1 is a request is ended
     */
    public void alertLoadingBar(int addOrSubtract){
        boolean status = model.operationIsBeingPerformed(addOrSubtract);
        if (status){
            if (!view.getLoadingHandler().beingDisplayed()){view.getLoadingHandler().displayLoadingCircle();}
        } else{
            if (view.getLoadingHandler().beingDisplayed()){view.getLoadingHandler().hideLoadingCircle();}
        }
    }

    /**
     * Updates the username the program uses to connect to reddit
     * @param username the username
     */
    public void updateRedditUsername(String username){
        if (username != null && !username.equals("")){
            model.getOutputHandler().setRedditUsernameAndPassword(username, null);
            view.redditUsernameSet();
        }
    }

    /**
     * Updates the password the program uses to connect to reddit
     * @param password the password
     */
    public void updateRedditPassword(String password){
        if (password != null && !password.equals("")){
            model.getOutputHandler().setRedditUsernameAndPassword(null, password);
            view.redditPasswordSet();
        }
    }

    /**
     * will update the views api text based on the model state
     */
    public void updateRedditKeyText(){
        if (model.areRedditKeysValid()){
            view.setRedditKeyText("Reddit API keys are set");
        } else{
            view.setRedditKeyText("Reddit API keys aren't set");
        }
    }

    /**
     * Toggles the music in the view, and updates the string accordingly
     */
    public void toggleMusic(){
        if (view.getMediaHandler().toggle()){
            view.setMusicEnabledString("Music enabled");
        } else{
            view.setMusicEnabledString("Music disabled");
        }
    }

    /**
     * Clears the cache and sends a notice to the user
     */
    public void clearCache(){
        view.createFadingText(725, 265, "Cache cleared", 5000);
        model.clearCache();
    }
}
