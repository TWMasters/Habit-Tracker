package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;

public interface TableState {
    /**
     * Add row to Table
     * @param values
     * @return Primary Key of entry just added
     */
    String addEntry(String[] values);

    /**
     * Delete specified row from Table
     * @param lookupValue
     */
    void deleteEntry(String lookupValue);

    /**
     * Edit specified row of Table
     * Implement by comparing values of entry at lookupValue with values supplied as parameter,
     * then changing the difference
     * @param values
     * @param lookupValue
     */
    String editEntry(String[] values, String lookupValue);

    /**
     * Get specified row of Table
     * @param lookupValue
     * @return ResultSet
     */
    ResultSet getEntry(String lookupValue);

    /**
     * Pull all data from Table
     * @return ResultSet
     */
    ResultSet getTable();

    /**
     * Method to get name of current TableState
     * @return
     */
    String getTableState();

    /**
     * Set context of new state
     * @param context Model connection to DB
     */
    void setContext(Connection context);
}
