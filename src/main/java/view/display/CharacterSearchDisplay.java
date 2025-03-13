package view.display;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.marvelObjects.Character;
import view.AppView;

import java.util.List;

public class CharacterSearchDisplay implements Display {
    private AppView view;
    private List<Character> characters;
    private VBox displayBox; //Pointer to where we display our thing
    private VBox resultBox; //What this looks like
    private boolean firstRun=true;

    public CharacterSearchDisplay(List<Character> characters, AppView view) {
        this.view = view;
        this.characters = characters;
        this.displayBox = view.getDisplayBox();
        setResultBox();
    }

    @Override
    public void setResultBox(){
        resultBox = new VBox();

        //Create title
        Text text = new Text();
        text.setText("Found the following characters (" + characters.size() + "): ");
        text.setFont(new Font(18));
        resultBox.getChildren().add(text);

        //Create box and button to look at character
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setMaxWidth(300);
        comboBox.setMinWidth(300);
        Button button = new Button();
        button.setText("Look at character");
        button.setOnAction(event -> {
            view.getController().getRequestController().selectCharacter(Character.getCharacterWithName(characters, comboBox.getValue()));
        });
        for (Character c : characters){
            comboBox.getItems().add(c.getName());
        }

        HBox hBox = new HBox(comboBox, button);
        resultBox.getChildren().add(hBox);
    }

    @Override
    public void display(){
        //Clear the current display area
        view.clearDisplayBox();

        //If its not our first runthrough, we need to set the display area
        if (!firstRun){ setResultBox();}
        firstRun = false;
        displayBox.getChildren().addAll(resultBox.getChildren());
    }
}
