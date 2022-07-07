package twm.habit_tracker.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.sql.ResultSet;
import java.sql.SQLException;

public class GUIView implements View {
    private final Button createHabit;
    private ObservableList<ObservableList> habitsTableData;
    private ResultSet habitsTableRS;
    private Stage window;
    private Scene scene1,  scene2;
    private TableView habitsTable;

    public GUIView() {
        this.createHabit = new Button("Add Habit");

    }


    /**
     * Create Habits Table
     * @return
     */
    private TableView<ResultSet> buildHabitsTable() throws SQLException {
        habitsTableData = FXCollections.observableArrayList();
        TableView<ResultSet> output = new TableView();

        for (int i = 0; i < habitsTableRS.getMetaData().getColumnCount(); i++) {
            // Create a Dynamic Table
            final int j = i;
            TableColumn col = new TableColumn(habitsTableRS.getMetaData().getColumnName(i+1).replace('_', ' '));

            output.getColumns().addAll(col);
        }
        return output;
    }

    @Override
    public void displayMessage(String title, String message) {
        AlertBox.display(title, message);
    }

    @Override
    public void setHabitsTableData(ResultSet resultSet) {
        habitsTableRS = resultSet;
    }

    @Override
    public void setCreateHabitListener(EventHandler<ActionEvent> onClick) {
        createHabit.setOnAction(onClick);
    }


    @Override
    public void setUp(Stage primaryStage) throws Exception {
        window = primaryStage;

        // Set up scene 1 Elements
        Label label1 = new Label("Welcome to the first scene");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));
        TableView habitsTable = buildHabitsTable();

        // Layout - scene1
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, createHabit, habitsTable, button1);
        scene1 = new Scene(layout1, 200, 200);

        // Set up scene 2 Elements
        Button button2 = new Button("Go to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        // Layout - scene2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2);

        // Contents
        window.setTitle("Habit Tracker");
        window.setMaximized(true);
        window.setScene(scene1);

        // ADD BACK
        // StackPane layout =  new StackPane();
        // layout.getChildren().add(createHabit);
    }
}
