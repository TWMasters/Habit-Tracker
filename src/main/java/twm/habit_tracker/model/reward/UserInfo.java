package twm.habit_tracker.model.reward;

import java.io.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInfo {
    private static final String FILE_NAME = "UserInfo.txt";
    private static final String PATH = System.getProperty("user.dir") + "\\db";


    public void createUserInfoFile(int levelCap) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("CoinBalance", 0);
        map.put("CoinTotal", 0);
        map.put("Level", 1);
        map.put("LevelCap", levelCap);
        writeToFile(map);
    }

    public int getBalance(int change) {
        Boolean flag = change > 0;
        HashMap<String, Integer> workingMap = readFromFile();
        workingMap.replace("CoinBalance", workingMap.get("CoinBalance") + change);
        if (flag) {
            workingMap.replace("CoinTotal", workingMap.get("CoinTotal") + change);
        }
        writeToFile(workingMap);
        return workingMap.get("CoinBalance");
    }

    /**
     * Fetch current level and level cap
     * Update if level cap has been breached!
     * @return Array with Level and Level Cap
     */
    public HashMap<String,Integer> getLevelData() {
        HashMap<String,Integer> levelMap = readFromFile();
        levelMap.remove("CoinBalance");
        return levelMap;

        /*
        int[] output = new int[3];
        output[0] = readFromFile().get("Level");
        output[1] = output[0] == MAX_LEVEL ? 1 : readFromFile().get("CoinTotal");
        output[2] = readFromFile().get("LevelCap");

         */
    }

    /**
     * Write new level and level cap to file
     */
    public void updateLevel(int newLvl, int newLvlCap) {
        HashMap<String,Integer> userInfo = readFromFile();
        userInfo.replace("Level", newLvl);
        userInfo.replace("LevelCap", newLvlCap);
        writeToFile(userInfo);
    }


    /**
     * Helper method to read file
     * @return associative array currently stored in file
     */
    private HashMap<String, Integer> readFromFile() {
        HashMap<String, Integer> tempMap = new HashMap<>();
        try {
            Scanner sc = new Scanner(new File(PATH, FILE_NAME));
            while(sc.hasNextLine()) {
                String[] line = sc.nextLine().split(":");
                tempMap.put(line[0], Integer.parseInt(line[1]));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file!");
            e.printStackTrace();
        }
        return tempMap;

    }

    /**
     * Helper method to write map to file!
     */
    private void writeToFile(HashMap<String, Integer> map) {
        File file = new File(PATH, FILE_NAME);
        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String,Integer> entry : map.entrySet()) {
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }
            bf.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bf.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
