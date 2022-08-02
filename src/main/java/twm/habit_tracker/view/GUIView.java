package twm.habit_tracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GUIView implements View {
    private static final String MENU_FRAME_URL = "src/main/java/twm/habit_tracker/view/menuFrame/MenuFrame.fxml";

    public static Node getFXMLResource(String local_path) {
        try {
            URL url = new File(local_path).toURI().toURL();
            return FXMLLoader.load(url);

        } catch (IOException e) {
            System.out.println("Problems when loading FXML File");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setUp(Stage primaryStage) {
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.DECORATED);

        Parent root = (Parent) getFXMLResource(MENU_FRAME_URL);
        Scene scene = new Scene(root); // Width, then Height
        primaryStage.setScene(scene);

        primaryStage.show();

    }
}
