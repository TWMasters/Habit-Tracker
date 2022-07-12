package twm.habit_tracker.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

    // TODO: 12/07/2022 Change to controller? 
    /**
     * Actions on exiting program
     */
    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Alert!", "Are you sure you want to exit?");
        if (answer) window.close();
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

        // Nested Layout
        HBox bottomMenu = new HBox(20);
        Button homeButton = new Button("Home");
        Button avatarButton = new Button("Avatar");
        bottomMenu.getChildren().addAll(homeButton, avatarButton);
        bottomMenu.setAlignment(Pos.CENTER);

        // Wiring
        addHabitbutton.setOnAction(e -> {
            window.setMaximized(true);
            window.setScene(habitPage);
        });

        // Layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(header, habitsTable, addHabitbutton, bottomMenu);
        layout.setAlignment(Pos.TOP_CENTER);
        return layout;
    }

    /**
     * Helper Method to assemble Habit page
     * @return Pane
     */
    public Pane getHabitPage() {
        // Layout
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(25, 25, 25, 25));

        //  Elements
        Text header = new Text("HABIT PAGE");
        layout.add(header, 1, 0, 2, 1);

        Label habitName = new Label("Habit Name: ");
        layout.add(habitName, 0, 1);
        TextField habitNameTF = new TextField();
        layout.add(habitNameTF, 1, 1, 2, 1);

        Label habitQuestion = new Label("Habit Question: ");
        layout.add(habitQuestion, 0, 2);
        TextField habitQuestionTF = new TextField();
        layout.add(habitQuestionTF, 1, 2, 2, 1);

        Button addHabitButton = new Button("Create");
        layout.add(addHabitButton, 1, 3, 2, 1);
        Button backButton = new Button("Back");
        layout.add(backButton, 1, 4, 2, 1);

        // Wiring
        addHabitButton.setOnAction(createHabitEventHandler);
        backButton.setOnAction(e -> {
            window.setMaximized(true);
            window.setScene(homePage);
        });

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

        // Window Contents
        window.setTitle("Habit Tracker");
        window.setOnCloseRequest(e -> {
            e.consume(); // Consume -> Use developer logic
            closeProgram();
        });

        // window.setMaximized(true);
        window.setScene(homePage);
    }

}
