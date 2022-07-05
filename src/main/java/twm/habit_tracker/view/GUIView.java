package twm.habit_tracker.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class GUIView extends View{
    Button button;

    public void run() {
        System.out.println("Launching!");
        launch();
    }

    @Override
    public void SetHabitDisplay(ResultSet HabitData) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Habit Tracker");
        button = new Button("Refresh");

        // Layout
        StackPane layout =  new StackPane();
        layout.getChildren().add(button);

        // Contents
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);

        // Display
        primaryStage.show();

    }
}
