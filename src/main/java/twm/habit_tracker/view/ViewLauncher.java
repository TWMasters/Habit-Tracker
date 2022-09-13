package twm.habit_tracker.view;

import javafx.scene.Node;
import javafx.stage.Stage;

public interface ViewLauncher {

    /**
     * Return page based on ID
     * @param pageID
     * @return Node
     */
    Node getPage(String pageID);

    /**
     * Set up stage at start
     * @param primaryStage
     */
    void setUp(Stage primaryStage);
}
