package twm.habit_tracker.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Helper Class which holds a single static method to create a Popup Menu
 */
public class PopupMenu {
    public static final Integer HALF = 2;

    public static void display(String title, String javafx_location) {

        Stage popupWindow = new Stage();
        popupWindow.initStyle(StageStyle.DECORATED);

        // Set up Stage
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setMinHeight(Screen.getPrimary().getBounds().getHeight() / HALF);
        popupWindow.setMinWidth(Screen.getPrimary().getBounds().getWidth() / HALF);
        popupWindow.setTitle(title);

        // Get JavaFX
        // Parent root = (Parent) GUIView.getFXMLResource(javafx_location);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root); // Width, then Height
        popupWindow.setScene(scene);
        popupWindow.showAndWait();

    }
}
