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
    View GUI;
    Model DB;
    Controller controller;

    public static void main(String[] args) {
        launch();
    }

    /**
     * Overridden init method to compose MVC prior to start()
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        GUI = new GUIView();
        DB = ConcreteModel.getModel();
        controller = new ConcreteController(GUI, DB);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GUI.setUp(primaryStage);
        primaryStage.show();
    }
}
