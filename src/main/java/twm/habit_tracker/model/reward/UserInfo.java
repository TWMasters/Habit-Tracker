package twm.habit_tracker.model.reward;

import java.io.File;

public class UserInfo {
    private static final String FILE_NAME = "UserInfo.txt";
    private static final String PATH = System.getProperty("user.dir") + "\\db";


    public void createUserInfoFile() {
        File file = new File(PATH, FILE_NAME);
    }
}
