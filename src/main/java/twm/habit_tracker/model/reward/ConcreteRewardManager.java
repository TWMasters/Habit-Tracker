package twm.habit_tracker.model.reward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class ConcreteRewardManager implements RewardManager {
    private static final int COIN_REWARD_COLUMN_NUMBER = 5;
    private static final int INCREMENT = 1;
    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final int MAX_LEVEL = 9;
    private static final int STARTING_BALANCE =  0;

    private final Connection connection;
    private AvatarState avatarState;
    private AvatarTable avatarTable;
    private LevelTable levelTable;
    private TrophyTable trophyTable;
    private UserInfo userInfo;

    public ConcreteRewardManager(Connection connection) {
        this.connection = connection;
        avatarState = new AvatarState();
        avatarTable = new AvatarTable(connection);
        levelTable =  new LevelTable(connection);
        trophyTable = new TrophyTable(connection);
        userInfo = new UserInfo();
    }

    @Override
    public void buildTables() {
        avatarState.createAvatarStateFile();
        avatarTable.createAvatarTable();
        levelTable.createLevelTable();
        trophyTable.createTrophyTable();
        int levelOneCap = levelTable.getLevelCap(LEVEL_ONE);
        int levelTwoCap = levelTable.getLevelCap(LEVEL_TWO);
        userInfo.createUserInfoFile(levelOneCap, levelTwoCap);
    }

    @Override
    public Optional<ResultSet> checkTrophies() {
        // Get Trophies
        Optional<ResultSet> trophyOutput = trophyTable.checkAwards();
        try {
            if (trophyOutput.isPresent()) {
                ResultSet workingRS = trophyOutput.get();
                int coinsAwarded = STARTING_BALANCE;
                while (workingRS.next()) {
                    coinsAwarded += workingRS.getInt(COIN_REWARD_COLUMN_NUMBER);
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
    public Optional<String> earnReward() {
        return Optional.empty();
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
    public HashMap<String,Integer> getLevel() {
        HashMap<String,Integer> levelMap = userInfo.getLevelData();
        if ( levelMap.get("Level") != MAX_LEVEL && levelMap.get("CoinTotal") >= levelMap.get("LevelCap") ) {
            int newLevel = levelMap.get("Level") + INCREMENT;
            int oldLevelCap = levelMap.get("LevelCap");
            int newLevelCap = levelTable.getLevelCap(newLevel + INCREMENT);
            userInfo.updateLevel(newLevel, oldLevelCap, newLevelCap);
            levelMap = userInfo.getLevelData();
        }
        return levelMap;
    }

    @Override
    public ResultSet getRewards() {
        return avatarTable.getRewardTable();
    }

    @Override
    public void updateTables() {
        trophyTable.updateDates();
    }
}
