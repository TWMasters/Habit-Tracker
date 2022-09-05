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

    public int[] getLevel() {
        int[] output = new int[2];
        return output;
    }

    public void updateLevel() {

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
