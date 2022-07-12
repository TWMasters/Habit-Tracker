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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.sql.ResultSet;
import java.sql.SQLException;

public class GUIView implements View {
    private EventHandler<ActionEvent> createHabitEventHandler;
    private ObservableList<String> habitsTableColumnData;
    private ObservableList<ObservableList<String>> habitsTableData;
    private Stage window;
    private Scene homePage, habitPage;

    /**
     * Create Habits Table based off Data matrix
     * @return a new Habits Table
     */
    public TableView<ObservableList<String>> buildHabitsTable() {

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
        System.out.println(ConfirmBox.display(title, message));
    }


    /**
     * Helper Method to assemble Home page
     * @return Pane
     */
    private Pane getHomePage() {
        // Elements
        Label header = new Label("HOME PAGE");
        TableView habitsTable = buildHabitsTable();
        Button addHabitbutton = new Button("Add Habit");

        // Wiring
        addHabitbutton.setOnAction(e -> {
            window.setMaximized(true);
            window.setScene(habitPage);
        });

        // Layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(header, habitsTable, addHabitbutton);
        return layout;
    }

    /**
     * Helper Method to assemble Habit page
     * @return Pane
     */
    public Pane getHabitPage() {
        //  Elements
        Label header = new Label("HABIT PAGE");
        Button addHabitButton = new Button("Create");
        Button backButton = new Button("Back");

        // Wiring
        addHabitButton.setOnAction(createHabitEventHandler);
        backButton.setOnAction(e -> {
            window.setMaximized(true);
            window.setScene(homePage);
        });

        // Layout
        VBox layout  = new VBox(20);
        layout.getChildren().addAll(header, addHabitButton, backButton);
        return layout;
    }

    /**
     * Helper method for Column metadata
     * @param resultSet
     */
    private void setHabitsTableColumnData(ResultSet resultSet, int columnCount) throws SQLException {
        habitsTableColumnData = FXCollections.observableArrayList();
        for (int i = 1; i <= columnCount; i++)
            habitsTableColumnData.add(resultSet.getMetaData().getColumnName(i));
    }

    /**
     * Send Habits table data to GUI for display by converting into
     * an Observable List matrix, which can then be used by the TableView
     * @param resultSet
     */
    @Override
    public void setHabitsTableData(ResultSet resultSet) {
        try {
            int columnCount = resultSet.getMetaData().getColumnCount();

            // Call helper method to save column metadata
            setHabitsTableColumnData(resultSet, columnCount);

            // Get Column Data as Array, and add to DataSet (Messy Algorithm)
            habitsTableData = FXCollections.observableArrayList();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    ObservableList<String> inputRow = FXCollections.observableArrayList();
                    for (int i = 1; i <= columnCount; i++ ) {
                        String input = resultSet.getString(i);
                        inputRow.add(input);
                    }
                    habitsTableData.add(inputRow);
                }
            }
            else
                System.out.println("No Rows!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCreateHabitListener(EventHandler<ActionEvent> e) {
        createHabitEventHandler = e;
    }


    @Override
    public void setUp(Stage primaryStage) throws Exception {
        window = primaryStage;

        homePage = new HabitScene(getHomePage()).buildScene();
        habitPage = new HabitScene(getHabitPage()).buildScene();

        // Contents
        window.setTitle("Habit Tracker");
        // window.setMaximized(true);
        window.setScene(homePage);
    }
}
