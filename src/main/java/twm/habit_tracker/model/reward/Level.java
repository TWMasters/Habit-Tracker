package twm.habit_tracker.model.reward;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Level {
    private static final String LEVEL_TABLE_SQL =
            "CREATE TABLE Level (\n" +
                    "Level INT PRIMARY KEY,\n" +
                    "Total_Coins INT NOT NULL" +
                    ");";

    private static final String POPULATE_LEVEL_TABLE =
            "INSERT INTO Level (Level, Total_Coins)\n" +
                    "VALUES\n" +
                    "(1, 0)," +
                    "(2, 85)," +
                    "(3, 315)," +
                    "(4, 730)," +
                    "(5, 2000)," +
                    "(6, 4000)," +
                    "(7, 8000)," +
                    "(8, 14000)," +
                    "(9, 24000);";

    private final Connection connection;

    public Level(Connection connection) {
        this.connection = connection;
    }

    public void createLevelTable() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(LEVEL_TABLE_SQL);
            stmt.execute(POPULATE_LEVEL_TABLE);
        }
        catch (SQLException e) {
            System.err.println("Error on creating Level Table");
            e.printStackTrace();
        }
    }

}
