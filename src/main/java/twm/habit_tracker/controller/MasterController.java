package twm.habit_tracker.controller;

import java.sql.SQLException;

public interface MasterController {

    /**
     * Use to hold methods for setting Avatar Page methods
     * @throws SQLException
     */
    void setAvatarPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Edit Habit Page methods
     * @throws SQLException
     */
    void setEditPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Habit Page methods
     * @throws SQLException
     */
    void setGoalPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Habit Page methods
     * @throws SQLException
     */
    void setHabitPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Menu Page methods
     * @throws SQLException
     */
    void setMenuPageMethods() throws SQLException;

    /**
     * Use to hold methods for setting Trophy Page methods
     * @throws SQLException
     */
    void setTrophyPageMethods() throws SQLException;

}

