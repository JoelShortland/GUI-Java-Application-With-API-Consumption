package controller.controllerComponents;

import controller.AppController;
import model.AppModel;
import model.history.HistoryEntry;
import view.AppView;

/**
 * Handles info from view that involves history
 */
public class HistoryController {
    private AppController controller;
    private AppModel model;
    private AppView view;

    public HistoryController(AppController controller, AppModel model, AppView view){
        this.controller = controller;
        this.model = model;
        this.view = view;
    }

    /**
     * Clear the program history
     */
    public void clearHistory(){
        view.createFadingText(725, 85, "History cleared", 5000);
        model.getHistoryHandler().clearHistory();
        view.clearHistoryBox();
    }

    /**
     * If we are switching the view to an item in history, get the item and display it
     * @param item the name of the character we are moving to
     */
    public void goToItemInHistory(String item){
        if (item == null){
            view.createFadingText(445, 565, "Please select an item", 3000);
            return;
        }
        HistoryEntry he = model.getHistoryHandler().getHistoryWithName(item);
        he.mark();
        he.getDisplay().display();
    }
}
