package controller;

import controller.controllerComponents.HistoryController;
import controller.controllerComponents.MenuController;
import controller.controllerComponents.RequestController;

import model.AppModel;

import view.AppView;


/**
 * The primary controller class
 */
public class AppController {
    private AppView view;
    private AppModel model;

    private HistoryController historyController;
    private MenuController menuController;
    private RequestController requestController;

    public AppController(AppModel model, AppView view){
        this.model = model;
        this.view = view;

        //set sub-controllers
        this.historyController = new HistoryController(this, model, view);
        this.menuController = new MenuController(this, model, view);
        this.requestController = new RequestController(this, model, view);

        //Update view depending on model
        menuController.updateRedditKeyText();
    }

    //Getters & Setters
    public AppModel getModel() {
        return model;
    }

    public AppView getView(){
        return view;
    }

    public HistoryController getHistoryController() {
        return historyController;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public RequestController getRequestController() {
        return requestController;
    }
}
