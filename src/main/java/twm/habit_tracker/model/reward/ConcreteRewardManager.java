package twm.habit_tracker.model.reward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ConcreteRewardManager implements RewardManager {
    private static final int LEVEL = 2;
    private static final int LEVEL_COLUMN_NUMBER = 2;

    private final Connection connection;
    private LevelTable levelTable;
    private TrophyTable trophyTable;
    private UserInfo userInfo;

    public ConcreteRewardManager(Connection connection) {
        this.connection = connection;
        levelTable =  new LevelTable(connection);
        trophyTable = new TrophyTable(connection);
        userInfo = new UserInfo();
    }

    @Override
    public void buildTables() {
        try {
            levelTable.createLevelTable();
            trophyTable.createTrophyTable();
            ResultSet rsLevelCap = levelTable.getLevelTableEntry(LEVEL);
            rsLevelCap.next();
            int levelCap = rsLevelCap.getInt(LEVEL_COLUMN_NUMBER);
            userInfo.createUserInfoFile(levelCap);
        }
        catch (SQLException e) {
            System.err.println("Error on fetching Level Cap when Building User Info File");
            e.printStackTrace();
        }
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
                userInfo.getBalance(coinsAwarded);
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
    public int getBalance() {
        return userInfo.getBalance(0);
    }

    @Override
    public void updateTables() {
        trophyTable.updateDates();
    }
}
