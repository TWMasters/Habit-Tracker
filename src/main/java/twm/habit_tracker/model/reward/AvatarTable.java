package twm.habit_tracker.model.reward;

public class AvatarTable {
    private static final String AVATAR_TABLE_SQL =
            "CREATE TABLE Avatar_Rewards (\n" +
                    "Reward_ID VARCHAR(255) PRIMARY KEY,\n" +
                    "Description VARCHAR(255) NOT NULL,\n" +
                    "Body_Part VARCHAR(255) NOT NULL, \n" +
                    "Level INT NOT NULL, \n" +
                    ");";
}
