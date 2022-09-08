package twm.habit_tracker.model.reward;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to manage the Avatar Reward Table
 */
class AvatarTable {
    private static final String AVATAR_TABLE_SQL =
            "CREATE TABLE Avatar_Rewards (\n" +
                    "Reward_ID VARCHAR(255) PRIMARY KEY,\n" +
                    "Description VARCHAR(255) NOT NULL,\n" +
                    "Body VARCHAR(255) NOT NULL, \n" +
                    "Level INT NOT NULL, \n" +
                    "Reward_Won BOOLEAN DEFAULT false NOT NULL" +
                    ");";

    private static final String FILE_NAME = "AvatarRewardData.txt";

    private static final String GET_REWARD_IDS = "SELECT Reward_ID FROM Avatar_Rewards WHERE Reward_Won = false AND Level <= %d;";

    private static final String GET_TABLE = "SELECT * FROM Avatar_Rewards WHERE Reward_Won = true;";

    private static final String PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\twm";

    private static final String POPULATE_AVATAR_TABLE_SQL =
            "INSERT INTO Avatar_Rewards (Reward_ID, Description, Body, Level)\n" +
                    "VALUES\n" +
                    "(\'%s\', \'%s\', \'%s\', %d);";

    private static final String UPDATE_REWARD =
            "UPDATE Avatar_Rewards SET Reward_Won = true WHERE Reward_ID = \'%s\'";;

    private final Connection connection;

    public AvatarTable(Connection connection) { this.connection = connection; }

    /**
     * Helper method used to select a random reward
     * @param level Player level cap
     * @return chosen reward
     */
    private String chooseReward(int level) throws SQLException {
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rsRewardIDs = stmt.executeQuery(String.format(GET_REWARD_IDS, level));
        if (rsRewardIDs.isBeforeFirst()) {
            ArrayList<String> idList =  new ArrayList<>();
            while (rsRewardIDs.next())
                idList.add(rsRewardIDs.getString(1));
            Random random = new Random();
            int index = random.nextInt(0, idList.size());
            return idList.get(index);
        }
        else
            return "no rewards available";
    }

    /**
     * Create and pre-populate avatar table based off pre-provided information in text file
     */
    public void createAvatarTable() {
        try (Scanner sc = new Scanner(new File(PATH, FILE_NAME))) {
            Statement stmt = connection.createStatement();
            stmt.execute(AVATAR_TABLE_SQL);
            while(sc.hasNextLine()) {
                String[] values = sc.nextLine().split(":");
                stmt.execute(String.format(POPULATE_AVATAR_TABLE_SQL, values[0], values[1], values[2], Integer.parseInt(values[3])));
            }
        } catch (SQLException e) {
            System.err.println("Error while creating statement in Avatar Class");
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            System.err.println("Error while loading Avatar Reward Data");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param level Player current level
     */
    public boolean updateRewardTable(int level) {
        try {
            String chosenRewardID = chooseReward(level);
        } catch (SQLException e) {
            System.err.println("Error while choosing a reward");
            e.printStackTrace();
        }
        return false;
    }
}
