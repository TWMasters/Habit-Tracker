package twm.habit_tracker.model.reward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ConcreteRewardManager implements RewardManager {
    private static final int COIN_REWARD_COLUMN_NUMBER = 5;
    private static final int INCREMENT = 1;
    private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final int MAX_LEVEL = 9;
    private static final int STARTING_BALANCE =  0;

    private final AvatarState avatarState;
    private final AvatarTable avatarTable;
    private final Connection context;
    private final LevelTable levelTable;
    private final TrophyTable trophyTable;
    private final UserInfo userInfo;

    public ConcreteRewardManager(Connection conn) {
        this.context = conn;
        avatarState = new AvatarState();
        avatarTable = new AvatarTable(context);
        levelTable =  new LevelTable(context);
        trophyTable = new TrophyTable(context);
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
    public void changeAvatarState(String key, String value) {
        avatarState.changeAvatarState(key, value);
    }

    /**
     * Helper method of earnReward to check if sufficient coins!
     * @param cost
     * @return True of False of whether user has sufficient coins
     */
    private boolean checkCost(int cost) {
        if (userInfo.getBalance(0) < cost)
            return false;
        userInfo.getBalance(-cost);
        return true;
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
    public ArrayList<String> earnReward(int noOfRewards, int cost) {
        ArrayList<String> outputMessages = new ArrayList<>();
        if (!checkCost(cost)) {
            outputMessages.add("Not enough coins to buy ticket!");
            return outputMessages;
        }
        int level = getLevel().get("Level");
        for (int i = 0; i < noOfRewards; i ++) {
            Optional<String> reward = avatarTable.updateRewardTable(level);
            if (reward.isEmpty()) {
                outputMessages.add("No rewards remaining!");
                break;
            }
            outputMessages.add("Congratulations!\nYou have been awarded a " + reward.get());
        }
        return outputMessages;
    }

    @Override
    public HashMap<String, String> getAvatarState() {
        return avatarState.getAvatarState();
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
