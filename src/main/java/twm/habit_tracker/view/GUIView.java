package twm.habit_tracker.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.sql.ResultSet;

public class GUIView implements View {
    private final Button createHabit;
    private ResultSet habitsTableData;
    private Stage window;
    private Scene scene1,  scene2;
    private TableView habitTable;

    public GUIView() {
        this.createHabit = new Button("Add Habit");
    }

    @Override
    public void displayMessage(String title, String message) {
        AlertBox.display(title, message);
    }

    @Override
    public void setHabitsTableData(ResultSet resultSet) {
        habitsTableData = resultSet;
    }

    @Override
    public void setCreateHabitListener(EventHandler<ActionEvent> onClick) {
        createHabit.setOnAction(onClick);
    }

    /*
    public TableView<ResultSet> setHabitDisplay(ResultSet HabitData) {
        TableView<ResultSet> output = new TableView();



    }

     */

    @Override
    public void setUp(Stage primaryStage) throws Exception {
        window = primaryStage;

        // Set up scene 1 Elements
        Label label1 = new Label("Welcome to the first scene");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));

        // Layout - scene1
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, createHabit, button1);
        scene1 = new Scene(layout1, 200, 200);

        // Set up scene 2 Elements
        Button button2 = new Button("Go to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        // Layout - scene2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 600, 300);

        // Contents
        window.setTitle("Habit Tracker");
        window.setScene(scene1);

        // ADD BACK
        // StackPane layout =  new StackPane();
        // layout.getChildren().add(createHabit);
    }
}
