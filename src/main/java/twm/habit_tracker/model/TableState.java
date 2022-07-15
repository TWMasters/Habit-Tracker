package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;

public interface TableState {
    /**
     * Pull all data from Table
     * @return ResultSet
     */
    ResultSet getTable();

    /**
     * Get specified row of Table
     * @param lookupValue
     * @return ResultSet
     */
    ResultSet getRow(String lookupValue);

    /**
     * Add row to Table
     * @param values
     */
    void addEntry(String[] values);

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
    void editEntry(String[] values, String lookupValue);

    /**
     * Set context of new state
     * @param context Model connection to DB
     */
    void setContext(Connection context);
}
