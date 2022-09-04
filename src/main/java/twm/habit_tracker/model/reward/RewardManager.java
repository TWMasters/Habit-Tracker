package twm.habit_tracker.model.reward;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface RewardManager {


    void buildTables();

    /**
     * Determine how many coins are to be awarded
     * Make change to coins file
     * @return ResultSet of awarded trophies for Controller to send messages
     */
    Optional<ResultSet> checkTrophies() throws SQLException;

    /**
     * Use to retrieve information on how many trophies have been awarded
     * for initial setup when app is launched
     * @return ResultSet of all awarded trophies
     */
    ResultSet getTrophies();

    void getCoins();

    void changeBalance(int coins);

    void updateTables();


}
