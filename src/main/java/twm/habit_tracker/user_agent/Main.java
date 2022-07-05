package twm.habit_tracker.user_agent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import twm.habit_tracker.controller.ConcreteController;
import twm.habit_tracker.controller.Controller;
import twm.habit_tracker.view.GUIView;
import twm.habit_tracker.view.View;


public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        View GUI = new GUIView();
        Controller controller = new ConcreteController(GUI);
        GUI.setUp(primaryStage);
        primaryStage.show();

    }
}
