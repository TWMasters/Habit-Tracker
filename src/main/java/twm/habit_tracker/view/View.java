package twm.habit_tracker.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.ResultSet;

public interface View {
    /**
     * Display a message
     * @param title
     * @param message
     */
    void displayMessage(String title, String message);

    /**
     * Set Event Handling for Create Habit button
     * @param event
     */
    void setCreateHabitListener(EventHandler<ActionEvent> event);

    /**
     * Update view of Habits
     * @param HabitData
     */
    void setHabitDisplay (ResultSet HabitData);

    /**
     * Set up stage at start
      * @param primaryStage
     */
    void setUp(Stage primaryStage) throws Exception;
}
