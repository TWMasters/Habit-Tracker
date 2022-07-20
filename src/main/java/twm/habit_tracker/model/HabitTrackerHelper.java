package twm.habit_tracker.model;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helper Class used by HabitTableState
 * Package-Private
 */
class HabitTrackerHelper {
    private static String ADD_COLUMN =
            "ALTER TABLE Habit_Tracker " +
            "ADD %s BOOLEAN DEFAULT false;";
    private static String DELETE_COLUMN =
            "ALTER TABLE Habit_Tracker " +
            "DROP COLUMN %s;";

    /**
     * Add Column to Habit Tracker Table
     * Called after adding a Habit to Habits Table
     * @param colName Name of Habit to be added as Column Name
     * @param stmt Statement from HabitTableState
     */
    public static void addColumn(String colName, Statement stmt) {
        try {
            stmt.execute(String.format(ADD_COLUMN, colName));

        } catch (SQLException e) {
            System.err.println("Error on Adding Column to Habit Tracker");
            e.printStackTrace();
        }
    };

    /**
     * Delete Column from Habit Tracker Table
     * Called after deleting a Habit from Habits Table
     * @param colName Name of Column to be deleted
     * @param stmt Statement from HabitTableState
     */
    public static void deleteColumn(String colName, Statement stmt) {
        try {
            stmt.execute(String.format(DELETE_COLUMN, colName));

        } catch (SQLException e) {
            System.err.println("Error on Adding Column to Habit Tracker");
            e.printStackTrace();
        }

    };
}
