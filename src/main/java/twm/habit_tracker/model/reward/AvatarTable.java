package twm.habit_tracker.model.reward;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class AvatarTable {
    private static final String AVATAR_TABLE_SQL =
            "CREATE TABLE Avatar_Rewards (\n" +
                    "Reward_ID VARCHAR(255) PRIMARY KEY,\n" +
                    "Description VARCHAR(255) NOT NULL,\n" +
                    "Body VARCHAR(255) NOT NULL, \n" +
                    "Level INT NOT NULL, \n" +
                    "Reward_WON BOOLEAN DEFAULT false NOT NULL" +
                    ");";

    private static final String FILE_NAME = "AvatarRewardData.txt";

    private static final String PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\twm";

    private static final String POPULATE_AVATAR_TABLE_SQL =
            "INSERT INTO Avatar_Rewards (Reward_ID, Description, Body, Level)\n" +
                    "VALUES\n" +
                    "(\'%s\', \'%s\', \'%s\', %d);";

    private final Connection connection;

    public AvatarTable(Connection connection) { this.connection = connection; }

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
}
