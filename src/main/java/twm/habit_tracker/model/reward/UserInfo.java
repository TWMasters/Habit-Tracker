package twm.habit_tracker.model.reward;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserInfo {
    private static final String FILE_NAME = "UserInfo.txt";
    private static final String PATH = System.getProperty("user.dir") + "\\db";


    public void createUserInfoFile() {


        HashMap<String, Integer> map = new HashMap<String, Integer>();

        map.put("CoinBalance", 0);
        map.put("CoinTotal", 0);
        map.put("Level", 1);

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
