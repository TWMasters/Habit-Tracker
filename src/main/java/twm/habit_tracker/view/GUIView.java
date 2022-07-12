package twm.habit_tracker.view;

import javafx.beans.property.SimpleStringProperty;
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
    private ObservableList<String> habitsTableColumnData;
    private ObservableList<ObservableList<String>> habitsTableData;
    private Stage window;
    private Scene scene1,  scene2;

    public GUIView() {
        this.createHabit = new Button("Add Habit");

    }

    /**
     * Create Habits Table based off Data matrix
     * @return a new Habits Table
     */
    private TableView<ObservableList<String>> buildHabitsTable() {

        TableView<ObservableList<String>> outputTable = new TableView();

        // Create a Dynamic Table
        for (int i = 0; i < habitsTableData.get(0).size(); i++) {
            // Create a Column
            TableColumn<ObservableList<String>, String> col =
                    new TableColumn(habitsTableColumnData.get(i).replace('_', ' '));
            final int j = i;
            if (habitsTableData.size() > 1)
                col.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().get(j)));
            outputTable.getColumns().add(col);
        }
        outputTable.setItems(habitsTableData);
        return outputTable;
    }

    @Override
    public void displayMessage(String title, String message) {
        AlertBox.display(title, message);
    }

    /**
     * Send Habits table data to GUI for display by converting into
     * an Observable List matrix, which can then be used by the TableView
     * @param resultSet
     */
    @Override
    public void setHabitsTableData(ResultSet resultSet) {
        try {
            habitsTableColumnData = FXCollections.observableArrayList();
            habitsTableData = FXCollections.observableArrayList();

            // Get Column Names and set up ArrayLists in Data
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                habitsTableColumnData.add(resultSet.getMetaData().getColumnName(i));
                habitsTableData.add(FXCollections.observableArrayList());
            }

            // Get Column Data as Array, and add to DataSet (Messy Algorithm)
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++ ) {
                        String input = resultSet.getString(i);
                        habitsTableData.get(i-1).add(input);
                    }
                }
            }
            else
                System.out.println("No Rows!");

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

        // ADD BACK AT SOME POINT
        // StackPane layout =  new StackPane();
        // layout.getChildren().add(createHabit);
    }
}
