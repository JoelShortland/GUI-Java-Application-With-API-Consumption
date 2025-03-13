import controller.AppController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.AppModel;
import view.AppView;

import java.util.List;

//https://developer.marvel.com/
//https://www.twilio.com/

public class Main extends Application {
    private final int width=900;
    private final int height=720;
    private final String programName="Marvel Application";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //First confirm args are fine.
        List<String> params = getParameters().getRaw();
        String param1 = "Offline";
        if (params.size() >= 1 && (params.get(0).equalsIgnoreCase("online"))){
            param1 = "Online";
        }

        String param2 = "Offline";
        if (params.size() >= 2 && (params.get(1).equalsIgnoreCase("online"))){
            param2 = "Online";
        }

        //Define params
        stage.setTitle(programName + " (" + param1 + ") (" + param2 + ")");
        stage.setMaxWidth(width);
        stage.setMaxHeight(height);

        //Create our app model and view
        AppModel model = new AppModel(param1, param2);
        AppView view = new AppView(stage, width, height);
        AppController controller = new AppController(model, view);
        view.setController(controller);
        model.setController(controller);
    }
}
