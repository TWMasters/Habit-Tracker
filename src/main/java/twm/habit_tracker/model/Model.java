package twm.habit_tracker.model;

import java.sql.ResultSet;

/**
 * @author tobym
 */
public interface Model {
    /**
     * Change target Table for Model methods to act upon
     * @param state
     */
    void changeTableState(State state);

    /**
     * Pull all data from Target Table
     * @return ResultSet
     */
    ResultSet getTable();

    /**
     * Get specified row of Target Table
     * @param lookupValue
     * @return ResultSet
     */
    ResultSet getRow(String lookupValue);

    /**
     * Add row to Target Table
     * @param values
     */
    void addEntry(String[] values);

    /**
     * Delete specified row from Target Table
     * @param lookupValue
     */
    void deleteEntry(String lookupValue);

    /**
     * Edit specified row of Target Table
     * Implement by comparing values of entry at lookupValue with values supplied as parameter,
     * then changing the difference
     * @param values
     * @param lookupValue
     */
    void editEntry(String[] values, String lookupValue);


}
