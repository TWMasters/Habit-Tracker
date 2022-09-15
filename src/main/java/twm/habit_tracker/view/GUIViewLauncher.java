package twm.habit_tracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Class for launching app window and setting window variables
 */
public class GUIViewLauncher implements ViewLauncher {
    private Parent menu_page;

    @Override
    public void setUp(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.DECORATED);

        try {
            menu_page = FXMLLoader.load(GUIViewLauncher.class.getResource("MenuPage.fxml"));
        } catch (IOException e) {
            System.out.println("Problems when loading Menu FXML File");
            e.printStackTrace();
        }
        Scene scene = new Scene(menu_page, 1000, 1200); // Width, then Height
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
