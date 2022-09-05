package twm.habit_tracker.model.reward;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public interface RewardManager {


    void buildTables();

    /**
     * Determine how many coins are to be awarded
     * Make change to coins file
     * @return ResultSet of awarded trophies for Controller to send messages
     */
    Optional<ResultSet> checkTrophies();

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

    void updateTables();


}
