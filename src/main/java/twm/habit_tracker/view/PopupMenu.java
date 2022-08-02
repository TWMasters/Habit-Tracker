package twm.habit_tracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Helper Class which holds a single static method to create a Popup Menu
 */
class PopupMenu {
    public static final Integer HALF = 2;

    public static void display(String title, String javafx_location) {
        Stage popupWindow = new Stage();

        // Set up Stage
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setMinHeight(Screen.getPrimary().getBounds().getHeight() / HALF);
        popupWindow.setMinWidth(Screen.getPrimary().getBounds().getWidth() / HALF);
        popupWindow.setTitle(title);

        // Get JavaFX
        try {
            URL popupURL =  new File(javafx_location).toURI().toURL();
            Parent root = FXMLLoader.load(popupURL);

            Scene scene = new Scene(root); // Width, then Height
            popupWindow.setScene(scene);
            popupWindow.show();

        } catch (IOException e) {
            System.err.println("Problems when producing popup window");
            e.printStackTrace();
        }

    }
}
