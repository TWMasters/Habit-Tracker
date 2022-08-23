package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helper Class for Concrete Model to create static Trophy Table
 */
class TrophyTable {
    private static final String TROPHY_TABLE_SQL =
            "CREATE TABLE Trophies (\n" +
                    "Trophy_ID VARCHAR(255) PRIMARY KEY,\n" +
                    "Trophy_Won BOOLEAN DEFAULT false NOT NULL,\n" +
                    "Trophy_Message VARCHAR(255) NOT NULL\n" +
                    ");";

    private static final String POPULATE_TROPHY_TABLE =
            "INSERT INTO Trophies (Trophy_ID, Trophy_Message)\n" +
                    "VALUES\n" +
                    "(\'HalfDay\', \'You Completed Half of your Daily Habits\')," +
                    "(\'FullDay\', \'You Completed All of your Daily Habits\')," +
                    "(\'ThreeDay\', \'You Completed your Daily Habits for Three Days this Week\')," +
                    "(\'FiveDay\', \'You Completed your Daily Habits for Five Days this Week\')," +
                    "(\'FullWeek\', \'You Completed your Daily Habits for the Week\')," +
                    "(\'TenDay\',\'You Completed your Daily Habits for Ten Days this Month\')," +
                    "(\'TwentyDay\', \'You Completed your Daily Habits for Twenty Days this Month\')," +
                    "(\'FullMonth\', \'You Completed your Daily Habits for Thirty Days this Month\');";

    public static void createTrophyTable(Connection connection)  {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(TROPHY_TABLE_SQL);
            stmt.executeQuery(POPULATE_TROPHY_TABLE);


        } catch (SQLException e) {
            System.err.println("Error on creating Trophy Table");
            e.printStackTrace();
        }
    }



}
