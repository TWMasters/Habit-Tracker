package twm.habit_tracker.model.reward;

import java.util.HashMap;
import java.util.Map;

class UserInfo {
    private static final String FILE_NAME = "UserInfo.txt";

    public void createUserInfoFile(int levelOneCap, int levelTwoCap) {
        HashMap<String, String> map = new HashMap<>();
        map.put("CoinBalance", "0");
        map.put("CoinTotal", "0");
        map.put("Level", "1");
        map.put("OldLevelCap", String.valueOf(levelOneCap));
        map.put("LevelCap", String.valueOf(levelTwoCap));
        FileHelper.writeToFile(map, FILE_NAME);
    }

    public int getBalance(int change) {
        Boolean flag = change > 0;
        HashMap<String, String> workingMap = FileHelper.readFromFile(FILE_NAME);
        int newCoinBalance = Integer.valueOf(workingMap.get("CoinBalance")) + change;
        workingMap.replace("CoinBalance", String.valueOf(newCoinBalance));
        if (flag) {
            int newCoinTotal = Integer.valueOf(workingMap.get("CoinTotal")) + change;
            workingMap.replace("CoinTotal", String.valueOf(newCoinTotal));
        }
        FileHelper.writeToFile(workingMap, FILE_NAME);
        return Integer.parseInt(workingMap.get("CoinBalance"));
    }

    /**
     * Fetch current level and level cap
     * @return Map of Level Data
     */
    public HashMap<String,Integer> getLevelData() {
        HashMap<String,String> stringLevelMap = FileHelper.readFromFile(FILE_NAME);
        HashMap<String,Integer> intLevelMap = new HashMap<>();
        for (Map.Entry<String,String> entry : stringLevelMap.entrySet()) {
            intLevelMap.put(entry.getKey(),Integer.valueOf(entry.getValue()));
        }
        intLevelMap.remove("CoinBalance");
        return intLevelMap;
    }

    /**
     * Write new level and level cap to file
     */
    public void updateLevel(int newLvl, int oldLvlCap, int newLvlCap) {
        HashMap<String,String> userInfo = FileHelper.readFromFile(FILE_NAME);
        userInfo.replace("Level", String.valueOf(newLvl));
        userInfo.replace("OldLevelCap", String.valueOf(oldLvlCap));
        userInfo.replace("LevelCap", String.valueOf(newLvlCap));
        FileHelper.writeToFile(userInfo, FILE_NAME);
    }

}
