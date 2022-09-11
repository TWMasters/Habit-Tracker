package twm.habit_tracker.model.reward;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Optional;

public interface RewardManager {

    /**
     * Use to build tables in database the first time the
     * app is launched by the user
     */
    void buildTables();

    /**
     * Determine how many coins are to be awarded
     * Make change to coins file
     * @return ResultSet of awarded trophies for Controller to send messages
     */
    Optional<ResultSet> checkTrophies();

    /**
     * Earn 1 random award
     * @param level Player level which caps reward
     * @return Name of new reward or null
     */
    Optional<String> earnReward(int level);

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
