package twm.habit_tracker.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
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
        System.out.println("Display popup!");

        Stage popupWindow = new Stage();
        popupWindow.initStyle(StageStyle.UNDECORATED);

        // Set up Stage
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setMinHeight(Screen.getPrimary().getBounds().getHeight() / HALF);
        popupWindow.setMinWidth(Screen.getPrimary().getBounds().getWidth() / HALF);
        popupWindow.setTitle(title);

        // Get JavaFX
        Parent root = (Parent) GUIView.getFXMLResource(javafx_location);
        Scene scene = new Scene(root); // Width, then Height
        popupWindow.setScene(scene);
        popupWindow.show();

    }
}
