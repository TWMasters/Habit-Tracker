package twm.habit_tracker.controller;

import java.sql.SQLException;

public interface Controller {

    /**
     * Use to hold methods for setting Edit Habit Page methods
     */
    void setEditPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Habit Page methods
     */
    void setGoalPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Habit Page methods
     */
    void setHabitPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Menu Page methods
     */
    void setMenuPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Trophy Page methods
     */
    void setTrophyPageMethods() throws SQLException;

}

