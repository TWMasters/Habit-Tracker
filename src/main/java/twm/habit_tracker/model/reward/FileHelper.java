package twm.habit_tracker.model.reward;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class FileHelper {
    private static final String PATH = System.getProperty("user.dir") + "\\db";

    /**
     * Helper method to read file
     * @return associative array currently stored in file
     */
    public static HashMap<String, String> readFromFile(String fileName) {
        HashMap<String, String> tempMap = new HashMap<>();
        try {
            Scanner sc = new Scanner(new File(PATH, fileName));
            while(sc.hasNextLine()) {
                String[] line = sc.nextLine().split(":");
                tempMap.put(line[0], line[1]);
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
    public static void writeToFile(HashMap<String, String> map, String fileName) {
        File file = new File(PATH, fileName);
        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String,String> entry : map.entrySet()) {
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
