package twm.habit_tracker.view;

import javafx.application.Application;

import java.sql.ResultSet;

public abstract class View extends Application {
    /**
     * Update view of Habits
     * @param HabitData
     */
    abstract void SetHabitDisplay (ResultSet HabitData);
}
