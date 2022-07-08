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
import java.util.Arrays;
import java.util.List;

public class GUIView implements View {
    private final Button createHabit;
    private ObservableList<ObservableList> habitsTableData;
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

        TableView<ResultSet> output = new TableView();

        for (int i = 0; i < habitsTableData.get(0).size(); i++) {
            // Create a Dynamic Table
            TableColumn col = new TableColumn(habitsTableData.get(0).get(i).toString().replace('_', ' '));
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
        try {
            habitsTableData = FXCollections.observableArrayList();
            // Add Column Names
            ObservableList<String> columnNames = FXCollections.observableArrayList();
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                // Get Column Name
                columnNames.add(resultSet.getMetaData().getColumnName(i + 1));
                // Get Column Data as Array, and add to DataSet
                if (resultSet.isBeforeFirst()) {
                    List colAsList = Arrays.asList(resultSet.getArray(i + 1));
                    ObservableList<String> colAsObservableList = FXCollections.observableArrayList(colAsList);
                    habitsTableData.add(colAsObservableList);
                }
            }
            // Add Array of Column Names to start
            habitsTableData.add(0, columnNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
