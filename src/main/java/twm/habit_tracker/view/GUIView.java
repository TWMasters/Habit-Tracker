package twm.habit_tracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GUIView implements View {
    private static final String MENU_FRAME_URL = "src/main/java/twm/habit_tracker/view/menuFrame/MenuFrame.fxml";


    @Override
    public void setUp(Stage primaryStage) {
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.DECORATED); // DECORATED for now. Change to UNDECORATED Later

        try {
            URL menuFrameURL =  new File(MENU_FRAME_URL).toURI().toURL();
            Parent root = FXMLLoader.load(menuFrameURL);

            Scene scene = new Scene(root); // Width, then Height
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Problems when loading MenuFrame FXML File");
            e.printStackTrace();
        }
    }
}
