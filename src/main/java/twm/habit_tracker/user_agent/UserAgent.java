package twm.habit_tracker.user_agent;

import javafx.application.Application;
import javafx.stage.Stage;
import twm.habit_tracker.controller.ConcreteController;
import twm.habit_tracker.controller.Controller;
import twm.habit_tracker.model.ConcreteModel_version_1;
import twm.habit_tracker.model.Model_version_1;
import twm.habit_tracker.view.GUIView;
import twm.habit_tracker.view.View;


public class UserAgent extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Compose application
        View GUI = new GUIView();
        Model_version_1 DB = ConcreteModel_version_1.getModel();
        Controller controller = new ConcreteController(GUI, DB);

        // Send primary stage to view to set-up
        GUI.setUp(primaryStage);
        primaryStage.show();
    }
}
