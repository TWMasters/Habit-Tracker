package twm.habit_tracker.user_agent;

import javafx.application.Application;
import javafx.stage.Stage;
import twm.habit_tracker.controller.ConcreteMasterController;
import twm.habit_tracker.controller.MasterController;
import twm.habit_tracker.model.ConcreteModel;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.GUIView;
import twm.habit_tracker.view.View;

public class UserAgent extends Application {
    View GUI;
    Model DB;
    MasterController masterController;

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
        masterController = new ConcreteMasterController(DB);

    }

    @Override
    public void start(Stage primaryStage) {
        GUI.setUp(primaryStage);
        primaryStage.show();
    }
}
