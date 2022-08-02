package twm.habit_tracker.controller;

import java.sql.SQLException;

public interface Controller {

    /**
     * Use to hold methods for setting Edit Habit Page methods
     */
    void setEditHabitPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Habit Page methods
     */
    void setGoalPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Habit Page methods
     */
    void setHabitPageMethods() throws SQLException;

}

