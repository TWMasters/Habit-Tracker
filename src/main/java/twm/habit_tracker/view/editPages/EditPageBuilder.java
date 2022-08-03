package twm.habit_tracker.view.editPages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


/**
 * Helper Class which holds a single static method to create a Popup Menu
 */
public class EditPageBuilder {
    public static final Integer HALF = 2;
    public static final String resource = "/editpages/HabitEditPages.fxml";

    public static void display(String title) {

        Stage popupWindow = new Stage();
        popupWindow.initStyle(StageStyle.DECORATED);

        // Set up Stage
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setMinHeight(Screen.getPrimary().getBounds().getHeight() / HALF);
        popupWindow.setMinWidth(Screen.getPrimary().getBounds().getWidth() / HALF);
        popupWindow.setTitle(title);

        // Get JavaFX
        try {
            Parent root = FXMLLoader.load(EditPageBuilder.class.getResource(resource));
            Scene scene = new Scene(root); // Width, then Height
            popupWindow.setScene(scene);
            popupWindow.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
