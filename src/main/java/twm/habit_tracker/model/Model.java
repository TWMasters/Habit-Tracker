package twm.habit_tracker.model;

import twm.habit_tracker.model.reward.RewardManager;

import java.sql.ResultSet;

/**
 * @author tobym
 */
public interface Model {
    /**
     * Add row to Target Table
     * @param values Values to populate new Row
     * @return Primary Key of entry just added
     */
    String addEntry(String[] values);

    /**
     * Change target Table for Model methods to act upon
     * @param newTargetTable New Target Table
     */
    void changeTargetTable(TableState newTargetTable);

    /**
     * End connection to Habits Database
     */
    void closeConnection();

    /**
     * Delete specified row from Target Table
     * @param lookupValue Primary Key Value of Target Row
     */
    void deleteEntry(String lookupValue);

    /**
     * Edit specified row of Target Table
     * @param values New Values to populate Target Row
     * @param lookupValue Primary Key Value of Target Row
     */
    String editEntry(String[] values, String lookupValue);

    /**
     * Get specified row of Target Table
     * @param lookupValue Primary Key Value of Target Row
     * @return ResultSet
     */
    ResultSet getEntry(String lookupValue);

    /**
     * Pull all data from Target Table
     * @return ResultSet
     */
    ResultSet getTable();

    /**
     * Get name of current Table State
     * @return name of Table State
     */
    String getTableState();

    /**
     * Get the reward manager for this model so can call methods
     * @return instance of Reward Manager
     */
    RewardManager getRewardManager();

}
