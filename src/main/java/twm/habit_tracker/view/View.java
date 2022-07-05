package twm.habit_tracker.view;

import javafx.application.Application;

import java.sql.ResultSet;

public abstract class View extends Application {
    public abstract void run();

    /**
     * Update view of Habits
     * @param HabitData
     */
    public abstract void SetHabitDisplay (ResultSet HabitData);
}
