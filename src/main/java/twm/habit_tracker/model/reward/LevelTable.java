package twm.habit_tracker.model.reward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to construct, populate, and fetch Level reference table
 */
public class LevelTable {
    private static final int LEVEL_CAP_COLUMN_NO = 2;

    private static final String LEVEL_TABLE_SQL =
            "CREATE TABLE Levels (\n" +
                    "Level INT PRIMARY KEY,\n" +
                    "Total_Coins INT NOT NULL" +
                    ");";

    private static final String POPULATE_LEVEL_TABLE =
            "INSERT INTO Levels (Level, Total_Coins)\n" +
                    "VALUES\n" +
                    "(1, 0)," +
                    "(2, 85)," +
                    "(3, 315)," +
                    "(4, 730)," +
                    "(5, 2000)," +
                    "(6, 4000)," +
                    "(7, 8000)," +
                    "(8, 14000)," +
                    "(9, 24000)," +
                    "(10, 1);";

    private static final String GET_LEVEL_TABLE =
            "SELECT * FROM Levels WHERE Level = %d;";

    private final Connection connection;

    public LevelTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Create and populate level table
     */
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

    /**
     * Fetch Level Table for reference
     * @return a ResultSet of Level Table
     */
    public int getLevelCap(int lvl) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(GET_LEVEL_TABLE, lvl));
            rs.next();
            return rs.getInt(LEVEL_CAP_COLUMN_NO);
        }
        catch (SQLException e) {
            System.err.println("Error in fetching Level Table");
            e.printStackTrace();
        }
        return 0;
    }

}
