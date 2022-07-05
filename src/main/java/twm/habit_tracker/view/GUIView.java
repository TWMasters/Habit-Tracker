package twm.habit_tracker.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.sql.ResultSet;

public class GUIView implements View {
    private final Button createHabit;

    public GUIView() {
        this.createHabit = new Button("Add Habit");
    }

    @Override
    public void setCreateHabitListener(EventHandler<ActionEvent> onClick) {
        createHabit.setOnAction(onClick);
    }

    @Override
    public void displayMessage(String text) {
        System.out.println(text);
    }

    @Override
    public void setHabitDisplay(ResultSet HabitData) {

    }

    // @Override
    public void setUp(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Habit Tracker");

        // Layout
        StackPane layout =  new StackPane();
        layout.getChildren().add(createHabit);

        // Contents
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
    }
}
