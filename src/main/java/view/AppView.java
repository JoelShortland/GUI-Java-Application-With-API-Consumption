package view;

import controller.AppController;
import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.history.HistoryEntry;
import view.optionalExtras.LoadingAlertHandler;
import view.optionalExtras.MediaHandler;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AppView {
    private AppController controller;
    private MediaHandler mediaHandler = new MediaHandler(this);
    private LoadingAlertHandler loadingAlertHandler;

    private final Stage stage;

    private final int sceneWidth;
    private final int sceneHeight;

    private final Group items;
    private final Scene scene;

    private final VBox searchResults=new VBox();
    private final ComboBox<String> historyBox = new ComboBox<>();

    //Strings updated from outside
    private final Text musicEnabledText = new Text("Music disabled");
    private final Text redditUserText = new Text("Username not set");
    private final Text redditPassText = new Text("Password not set");
    private final Text redditKeyText = new Text();
    private Text pinnedComicText = new Text("None"); //Create a reference to the pinned comic text

    public AppView(Stage stage, int sceneWidth, int sceneHeight){
        //set given variables
        this.stage = stage;
        this.sceneWidth=sceneWidth;
        this.sceneHeight=sceneHeight;

        //Define our scene
        this.items = new Group();
        this.scene = new Scene(items, sceneWidth, sceneHeight);

        //Configure Search result Display
        searchResults.setLayoutY(50);
        searchResults.setLayoutX(20);
        this.items.getChildren().add(searchResults);

        //Create objects
        createSearchBar();
        createHistoryStore();
        createMenu();
        createPinnedBox();

        //Create our handler for the loading wheel
        this.loadingAlertHandler = new LoadingAlertHandler(items);

        //And display our stage
        stage.setScene(scene);
        stage.show();
    }

    //Getters and Setters
    public LoadingAlertHandler getLoadingHandler(){
        return this.loadingAlertHandler;
    }

    public AppController getController(){
        return this.controller;
    }

    public void setController(AppController controller){
        this.controller = controller;
    }

    public MediaHandler getMediaHandler(){ return this.mediaHandler;}

    public void clearDisplayBox(){
        this.searchResults.getChildren().clear();
    }

    public VBox getDisplayBox(){
        return this.searchResults;
    }

    public void redditUsernameSet(){
        this.redditUserText.setText("Username set");
    }

    public void redditPasswordSet(){
        this.redditPassText.setText("Password set");
    }

    public void setRedditKeyText(String text){
        this.redditKeyText.setText(text);
    }

    public void setMusicEnabledString(String status){
        musicEnabledText.setText(status);
    }

    public void clearHistoryBox(){
        this.historyBox.getItems().clear();
    }

    /**
     * Displays text that will fade over time
     * @param xPos xpos of the text
     * @param yPos ypos of the text
     * @param text the text
     * @param durationMillis the time the text will take to fade completely
     */
    public void createFadingText(double xPos, double yPos, String text, int durationMillis){
        Text t = new Text( xPos, yPos,text);
        FadeTransition ft = new FadeTransition(Duration.millis(durationMillis), t);
        ft.setFromValue(1);
        ft.setToValue(0);
        this.items.getChildren().add(t);
        ft.play();
    }

    /**
     * Update the contents of the history box (Note: requires loop due to how I implemented the history list)
     */
    public void updateHistoryBox(){
        historyBox.getItems().clear();
        HistoryEntry currentEntry = controller.getModel().getHistoryHandler().getRootEntry();
        while (currentEntry != null){
            historyBox.getItems().add(currentEntry.getName());
            currentEntry = currentEntry.getNextEntry();
        }
    }

    /**
     * Create the display for the pinned comic
     */
    public void createPinnedBox(){
        //Title Text
        Text pinnedComicTitle = new Text();
        pinnedComicTitle.setText("Pinned Comic");
        pinnedComicTitle.setFont(new Font(24));
        pinnedComicText.setFont(new Font(18));

        //Create background
        Rectangle rectangle = new Rectangle(425, 200, (Paint.valueOf("darkgray")));
        rectangle.setLayoutX(475);
        rectangle.setLayoutY(540);
        items.getChildren().add(rectangle);

        //create clear pinned character button
        Button button = new Button();
        button.setText("Clear Pinned Comic");
        button.setOnAction(event -> {
            controller.getModel().getPinnedHandler().resetPinnedComic();
            updatePinnedBox();
        });

        //Configure background
        VBox vBox = new VBox(pinnedComicTitle, pinnedComicText, button);
        vBox.setSpacing(20);
        vBox.setLayoutX(490);
        vBox.setLayoutY(550);
        items.getChildren().add(vBox);
    }

    /**
     * Creates the search bar
     */
    private void createSearchBar(){
        TextField textField = new TextField();
        textField.setMinWidth(300);
        Text text = new Text();
        text.setText("Enter Name: ");
        Button button = new Button();
        button.setText("Search");
        button.setOnAction((event -> controller.getRequestController().search(this, textField.getText())));

        HBox hBox = new HBox(text, textField, button);
        hBox.setLayoutX(20);
        hBox.setLayoutY(20);
        items.getChildren().add(hBox);
    }

    /**
     * Creates the menu on the right side of the application
     */
    private void createMenu(){
        //Menu text
        Text menuText = new Text("Menu");
        menuText.setFont(new Font(24));

        //Clear history button
        Button clearHistoryButton = new Button();
        clearHistoryButton.setText("Clear history");
        clearHistoryButton.setOnAction(event -> {
            controller.getHistoryController().clearHistory();
        });
        VBox clearHistoryVBox = new VBox(new Text(), clearHistoryButton);

        //Send message button
        Button sendHistoryButton = new Button();
        sendHistoryButton.setText("Send History To Number");
        sendHistoryButton.setOnAction(event -> controller.getRequestController().sendMessage(controller.getModel().getHistoryHandler().getHistory()));
        VBox sendHistoryVBox = new VBox(new Text(), sendHistoryButton);

        //play music
        Button toggleMusicButton = new Button("Toggle Music");
        toggleMusicButton.setOnAction(event ->  controller.getMenuController().toggleMusic());
        VBox toggleMusicVBox = new VBox(musicEnabledText, toggleMusicButton);

        //Clear cache
        Button clearCacheButton = new Button("Clear Cache");
        clearCacheButton.setOnAction(event -> controller.getMenuController().clearCache());
        VBox clearCacheVBox = new VBox(new Text(), clearCacheButton);

        //Post to reddit
        Button sendRedditMessageButton = new Button("Send History To Reddit");
        sendRedditMessageButton.setOnAction(event -> controller.getRequestController().sendRedditMessage());
        VBox sendRedditMessageVBox = new VBox(redditKeyText, sendRedditMessageButton);

        //Set reddit credentials
        Button setRedditUsernameButton = new Button("Set reddit username");
        setRedditUsernameButton.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("Enter reddit username");
            textInputDialog.showAndWait();
            controller.getMenuController().updateRedditUsername(textInputDialog.getResult());
        });
        VBox redditUsernameVBox = new VBox(redditUserText, setRedditUsernameButton);

        Button setRedditPasswordButton = new Button("Set reddit password");
        setRedditPasswordButton.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("Enter reddit password");
            textInputDialog.showAndWait();
            controller.getMenuController().updateRedditPassword(textInputDialog.getResult());
        });
        VBox redditPasswordVBox = new VBox(redditPassText, setRedditPasswordButton);

        //Take screenshot
        Button takeScreenshotButton = new Button("Take Screenshot Of Scene");
        takeScreenshotButton.setOnAction(event ->{
            takeScreenshot();
        });
        VBox screenshotVBox = new VBox(new Text(), takeScreenshotButton);

        //Create menu
        Rectangle rectangle = new Rectangle(200, sceneHeight, (Paint.valueOf("grey")));
        rectangle.setLayoutX(sceneWidth-200);
        rectangle.setLayoutY(0);
        items.getChildren().add(rectangle);

        VBox vBox = new VBox(menuText, clearHistoryVBox, sendHistoryVBox, screenshotVBox, clearCacheVBox, toggleMusicVBox, redditUsernameVBox, redditPasswordVBox, sendRedditMessageVBox);
        vBox.setSpacing(20);
        vBox.setLayoutX(725);
        vBox.setLayoutY(20);
        items.getChildren().add(vBox);
    }

    /**
     * Try and take a screenshot of the current screen state
     */
    public void takeScreenshot(){
        //Take a snippet of the current window
        Robot robot = new Robot();
        WritableImage writableImage = robot.getScreenCapture(null, new Rectangle2D(stage.getX(), stage.getY(), sceneWidth, sceneHeight));

        //Create a filechooser to save the image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.setInitialFileName("Screenshot_"+String.valueOf(System.currentTimeMillis())+".png");

        //Save the file
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage,null), "png", file);
                createFadingText(725, 205, "Screenshot saved", 5000);
            } catch (IOException ex) {
                createFadingText(725, 205, "Failed to save screenshot", 5000);
            }
        }
    }

    /**
     * Creates the history bar and corrisponding button
     */
    private void createHistoryStore(){
        historyBox.setMinWidth(300);
        historyBox.setMaxWidth(300);
        Text text = new Text();
        text.setText("History: ");
        Button button = new Button();
        button.setText("Go To Item");
        button.setOnAction((event -> {
            controller.getHistoryController().goToItemInHistory(historyBox.getValue());
        }));

        HBox hBox = new HBox(text, historyBox, button);
        hBox.setLayoutX(20);
        hBox.setLayoutY(550);
        items.getChildren().add(hBox);
    }

    /**
     * Create an alert when cached data is found
     * @return the response to the alert
     */
    public CacheResponse createCacheAlert(){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Cached Content Conformation");
        alert.setHeaderText("Cached content found for selected item");
        alert.setContentText("Use cached content?");
        ButtonType buttonType1 = new ButtonType("Use Cached Content");
        ButtonType buttonType2 = new ButtonType("Use a fresh search");

        alert.getButtonTypes().addAll(buttonType1, buttonType2);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonType1){
            return CacheResponse.USE_CACHE;
        } else if (result.get() == buttonType2) {
            return CacheResponse.USE_NEW;
        }  else {
            return CacheResponse.CANCEL;
        }
    }
    /**
     * Update the name of the pinned comic
     */
    public void updatePinnedBox(){
        pinnedComicText.setText(controller.getModel().getPinnedHandler().getPinnedComic());
    }

    /**
     * Creates a message type alert
     * @param alertContent The content of the alert
     */
    public void createMessageAlert(String alertContent){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Alert");
        a.setContentText(alertContent);
        a.show();
    }
}
