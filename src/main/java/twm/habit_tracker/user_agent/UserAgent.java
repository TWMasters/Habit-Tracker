package twm.habit_tracker.user_agent;

import javafx.application.Application;
import javafx.stage.Stage;
import twm.habit_tracker.controller.ConcreteMasterController;
import twm.habit_tracker.controller.MasterController;
import twm.habit_tracker.model.ConcreteModel;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.GUIViewLauncher;
import twm.habit_tracker.view.ViewLauncher;

import java.sql.SQLException;

/**
 * Initialise MVC and JavaFX application
 */
public class UserAgent extends Application {
    ViewLauncher GUI;
    Model DB;
    MasterController masterController;

    public static void main(String[] args) {
        launch();
    }

    /**
     * Overridden init method to compose MVC prior to start()
     * @throws SQLException on loading Concrete Master Controller
     */
    @Override
    public void init() throws SQLException {
        GUI = new GUIViewLauncher();
        DB = ConcreteModel.getModel();
        masterController = new ConcreteMasterController(DB);

    }

    @Override
    public void start(Stage primaryStage) {
        GUI.setUp(primaryStage);
        primaryStage.show();
    }
}
