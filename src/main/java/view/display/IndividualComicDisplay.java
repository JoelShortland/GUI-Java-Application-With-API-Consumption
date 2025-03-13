package view.display;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.marvelObjects.DisplayComic;
import view.AppView;

import java.util.ArrayList;
import java.util.List;

public class IndividualComicDisplay  implements Display{
    private AppView view;
    private DisplayComic comic;
    private VBox displayBox;
    private VBox resultBox;
    private boolean firstRun=true;

    public IndividualComicDisplay(DisplayComic comic, AppView view){
        this.view = view;
        this.comic = comic;
        this.displayBox = view.getDisplayBox();
        setResultBox();
    }

    @Override
    public void setResultBox() {
        resultBox = new VBox();

        //For formatting
        Text text = new Text();
        text.setFont(new Font(18));
        resultBox.getChildren().add(text);

        //Create box with characters
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setMinWidth(300);
        comboBox.setMaxWidth(300);
        Button button = new Button();
        button.setText("Look at character");
        button.setOnAction(event -> view.getController().getRequestController().selectCharacter(comboBox.getValue()));

        //Get characters in comic
        List<String> characterList = comic.getCharacters();
        if (characterList == null) { characterList = new ArrayList<>();}
        for (String s : characterList){
            comboBox.getItems().add(s);
        }

        //Create a pin comic button
        Button button2 = new Button();
        button2.setText("Pin Comic");
        button2.setOnAction(event -> {
            view.getController().getModel().getPinnedHandler().setPinnedComic(comic);
            view.updatePinnedBox();
        });


        //Create title
        text.setText(comic.getName() + " has the following characters (" + comboBox.getItems().size() + "): ");
        HBox hBox = new HBox(comboBox, button, button2);
        resultBox.getChildren().add(hBox);
    }

    @Override
    public void display() {
        //Display the contents of this class in the main view
        displayBox.getChildren().clear();
        if (!firstRun){ setResultBox();}
        firstRun = false;
        displayBox.getChildren().addAll(resultBox.getChildren());
    }
}
