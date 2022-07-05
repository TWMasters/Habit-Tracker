package twm.habit_tracker.view;

import javafx.application.Application;

import java.sql.ResultSet;

public abstract class View extends Application {
    /**
     * Call to launch application from another Class
     */
    public abstract void run();

    public abstract void setCreateHabitListener();

    /**
     * Update view of Habits
     * @param HabitData
     */
    public abstract void SetHabitDisplay (ResultSet HabitData);
}
