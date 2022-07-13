package twm.habit_tracker.controller;

import java.sql.SQLException;

public interface Controller {

    /**
     * Refresh View with updated Habit Data
     */
    void refreshData() throws SQLException;

}

