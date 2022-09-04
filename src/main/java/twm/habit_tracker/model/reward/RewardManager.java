package twm.habit_tracker.model.reward;

import java.sql.ResultSet;

public interface RewardManager {


    void buildTables();

    /**
     * Determine how many coins are to be awarded
     * Make change to coins file
     *
     * @return ResultSet for Controller
     */
    ResultSet checkTrophies();

    void getCoins();

    void changeBalance(int coins);

    void updateTables();


}
