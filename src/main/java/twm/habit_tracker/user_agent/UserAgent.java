package twm.habit_tracker.user_agent;

import javafx.application.Application;
import javafx.stage.Stage;
import twm.habit_tracker.controller.ConcreteController;
import twm.habit_tracker.controller.Controller;
import twm.habit_tracker.model.ConcreteModel;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.GUIView;
import twm.habit_tracker.view.View;


public class UserAgent extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        View GUI = new GUIView();
        Model DB = ConcreteModel.getModel();
        Controller controller = new ConcreteController(GUI, DB);
        // Send to view to set-up
        GUI.setUp(primaryStage);
        primaryStage.show();

    }
}
