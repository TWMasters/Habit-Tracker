package twm.habit_tracker.model.reward;

import java.util.HashMap;

class UserInfo {
    private static final String FILE_NAME = "UserInfo.txt";

    public void createUserInfoFile(int levelOneCap, int levelTwoCap) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("CoinBalance", 0);
        map.put("CoinTotal", 0);
        map.put("Level", 1);
        map.put("OldLevelCap", levelOneCap);
        map.put("LevelCap", levelTwoCap);
        FileHelper.writeToFile(map, FILE_NAME);
    }

    public int getBalance(int change) {
        Boolean flag = change > 0;
        HashMap<String, Integer> workingMap = FileHelper.readFromFile(FILE_NAME);
        workingMap.replace("CoinBalance", workingMap.get("CoinBalance") + change);
        if (flag) {
            workingMap.replace("CoinTotal", workingMap.get("CoinTotal") + change);
        }
        FileHelper.writeToFile(workingMap, FILE_NAME);
        return workingMap.get("CoinBalance");
    }

    /**
     * Fetch current level and level cap
     * @return Map of Level Data
     */
    public HashMap<String,Integer> getLevelData() {
        HashMap<String,Integer> levelMap = FileHelper.readFromFile(FILE_NAME);
        levelMap.remove("CoinBalance");
        return levelMap;

    }

    /**
     * Write new level and level cap to file
     */
    public void updateLevel(int newLvl, int oldLvlCap, int newLvlCap) {
        HashMap<String,Integer> userInfo = FileHelper.readFromFile(FILE_NAME);
        userInfo.replace("Level", newLvl);
        userInfo.replace("OldLevelCap", oldLvlCap);
        userInfo.replace("LevelCap", newLvlCap);
        FileHelper.writeToFile(userInfo, FILE_NAME);
    }

}
