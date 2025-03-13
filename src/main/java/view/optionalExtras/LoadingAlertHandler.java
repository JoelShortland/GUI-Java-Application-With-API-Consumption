package view.optionalExtras;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Handles the loading icon
 */
public class LoadingAlertHandler {
    private Group items;
    private ImageView theImage;
    private boolean displayed=false;

    public LoadingAlertHandler(Group items){
        this.items = items;
        //Define loading bar
        String gifName = "spinning-loading.gif";

        Image image = new Image(("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/" + gifName).replace(gifName, "src" + "/" + "main" + "/" + "resources" + "/" + gifName));
        this.theImage = new ImageView(image);
        theImage.setX(0);
        theImage.setY(600);
        theImage.setPreserveRatio(true);
        theImage.setFitWidth(200);
    }

    public void displayLoadingCircle(){
        displayed = true;
        this.items.getChildren().add(theImage);
    }

    public void hideLoadingCircle(){
        displayed = false;
        this.items.getChildren().remove(theImage);
    }

    public boolean beingDisplayed(){
        return this.displayed;
    }

}
