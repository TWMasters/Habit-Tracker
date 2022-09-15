package twm.habit_tracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Class for launching app window and setting window variables
 */
public class GUIViewLauncher implements ViewLauncher {
    private static String CSS = Path.of("src/main/resources/twm/habit_tracker/view/stylesheet.css").toUri().toString();

    private Parent menu_page;
    private HashMap<String, Node> pages = new HashMap<>();


    @Override
    public Node getPage(String pageID) {
        return pages.get(pageID);
    }

    @Override
    public void setUp(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.DECORATED);

        // Load Pages!
        try {
            menu_page = FXMLLoader.load(GUIViewLauncher.class.getResource("MenuPage.fxml"));
        } catch (IOException e) {
            System.out.println("Problems when loading Menu FXML File");
            e.printStackTrace();
        }

        Scene scene = new Scene(menu_page, 1000, 1200); // Width, then Height
        scene.getStylesheets().add(CSS);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
