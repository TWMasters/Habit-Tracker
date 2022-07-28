package twm.habit_tracker.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIView implements View {
    @Override
    public void setUp(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.DECORATED); // DECORATED for now. Change to UNDECORATED Later

        Label hello = new Label("Hello");
        StackPane layout = new StackPane(hello);
        Scene scene = new Scene(layout, 360, 640); // Width, then Height

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
