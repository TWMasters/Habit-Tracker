package twm.habit_tracker.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface View {
    /**
     * Display a message
     * @param title
     * @param message
     */
    void displayMessage(String title, String message);

    /**
     * Use to gather Habit Information from text-fields
     * when creating a new Habit or modifying an existing Habit
     * @return Habit Record
     */
    Habit getHabitInfo();

    /**
     * Set Event Handling for Create Habit button
     * @param event
     */
    void setCreateHabitListener(EventHandler<ActionEvent> event);

    /**
     * Controller uses to send Habits Table Data to GUI for display
     * @param resultSet
     */
    void setHabitsTableData(ResultSet resultSet) throws SQLException;

    /**
     * Set up stage at start
      * @param primaryStage
     */
    void setUp(Stage primaryStage) throws Exception;
}
