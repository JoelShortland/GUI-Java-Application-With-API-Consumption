package view.display;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.marvelObjects.Character;
import model.marvelObjects.DisplayComic;
import view.AppView;

import java.util.List;

public class IndividualCharacterDisplay implements Display{
    private AppView view;
    private Character character;
    private VBox displayBox;
    private VBox resultBox;
    private boolean firstRun=true;

    public IndividualCharacterDisplay(Character character, AppView view) {
        this.view = view;
        this.character = character;
        this.displayBox = view.getDisplayBox();
        setResultBox();

    }

    @Override
    public void setResultBox() {
        //Set resultBox
        resultBox = new VBox();

        //Set text
        Text text = new Text();
        text.setText(character.getName());
        text.setFont(new Font(24));

        //Set image
        String path = character.getThumbnailPath();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300); //uncanny is 300x450
        imageView.setPreserveRatio(true);

        //Set combobox of comics
        List<DisplayComic> comics = view.getController().getRequestController().getComicsOfCharacter(character.getId());
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setMaxWidth(250);
        comboBox.setMinWidth(250);

        //Create button and fill in combobox that goes with it
        Button button = new Button();
        button.setText("Go To Comic");
        button.setOnAction(event -> {
            view.getController().getRequestController().selectComic(DisplayComic.getComicWithName(comics, comboBox.getValue()));
        });
        for (DisplayComic c : comics){
            comboBox.getItems().add(c.getName());
        }

        //define title
        Text text1 = new Text("Character is in comics (" + comics.size() +"):");
        text1.setFont(new Font(24));

        //And configure resultBox
        VBox vBox = new VBox(text, imageView);
        HBox hBox1 = new HBox(comboBox, button);
        VBox vBox1 = new VBox(text1, hBox1);
        HBox hBox = new HBox(vBox, vBox1);
        hBox.setSpacing(20);
        resultBox.getChildren().addAll(hBox);
    }

    @Override
    public void display() {
        displayBox.getChildren().clear();
        if (!firstRun){ setResultBox();}
        firstRun = false;
        displayBox.getChildren().addAll(resultBox.getChildren());
        if (view.getController().getModel().getPinnedHandler().checkIfCharacterInPinnedComic(character.getName())){
            view.createMessageAlert("This character is in your pinned comic");
        }
    }
}
