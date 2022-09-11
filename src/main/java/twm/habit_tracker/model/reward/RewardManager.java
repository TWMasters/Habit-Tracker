package twm.habit_tracker.model.reward;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public interface RewardManager {

    /**
     * Use to build tables in database the first time the
     * app is launched by the user
     */
    void buildTables();

    /**
     * Change avatar state record
     * @param key Avatar Component to be changed
     * @param value New value of Avatar Component
     */
    void changeAvatarState(String key, String value);

    /**
     * Determine how many coins are to be awarded
     * Make change to coins file
     * @return ResultSet of awarded trophies for Controller to send messages
     */
    Optional<ResultSet> checkTrophies();

    /**
     * Earn 1 random award
     * @param noOfRewards How many rewards the player can earn
     * @return Name of new reward or alternative message
     */
    ArrayList<String> earnReward(int noOfRewards);

    /**
     * Use to retrieve current state of user avatar
     * @return Hashmap associative array of avatar section keys and assigned reward values
     */
    HashMap<String,String> getAvatarState();

    /**
     * Use to retrieve information on how many trophies have been awarded
     * for initial setup when app is launched
     * @return ResultSet of all awarded trophies
     */
    ResultSet getTrophies();

    /**
     * Get balance of coins
     */
    int getBalance();

    /**
     * Return Level Data
     * @return Map of Level Data
     */
    HashMap<String, Integer> getLevel();

    /**
     * Use to retrieve all avatar rewards which have been earned by the user
     * @return ResultSet of current avatar rewards
     */
    ResultSet getRewards();

    /**
     * Use to update tables when first launching app for a given day
     * Needed to change dates
     */
    void updateTables();


}
