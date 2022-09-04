package twm.habit_tracker.model.reward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ConcreteRewardManager implements RewardManager {
    private final Connection connection;
    private TrophyTable trophyTable;
    private UserInfo userInfo;

    public ConcreteRewardManager(Connection connection) {
        this.connection = connection;
        trophyTable = new TrophyTable(connection);
        userInfo = new UserInfo();
    }


    @Override
    public void buildTables() {
        trophyTable.createTrophyTable();
        userInfo.createUserInfoFile();
    }

    @Override
    public Optional<ResultSet> checkTrophies() {
        // Get Trophies
        Optional<ResultSet> trophyOutput = trophyTable.checkAwards();
        try {
            if (trophyOutput.isPresent()) {
                ResultSet workingRS = trophyOutput.get();
                int coinsAwarded = 0;
                while (workingRS.next()) {
                    coinsAwarded += workingRS.getInt(5);
                }
                // Change coins
                System.out.println("Coins" + coinsAwarded);
                // Reset before returning!
                workingRS.beforeFirst();
            }
        }
        catch (SQLException e) {
            System.err.println("Error on resetting trophy result set");
            e.printStackTrace();
        }
        return trophyOutput;
    }


    @Override
    public ResultSet getTrophies() {
        return trophyTable.getTrophyTable();
    }

    @Override
    public void getCoins() {

    }

    @Override
    public void changeBalance(int coins) {

    }

    @Override
    public void updateTables() {
        trophyTable.updateDates();
    }
}
