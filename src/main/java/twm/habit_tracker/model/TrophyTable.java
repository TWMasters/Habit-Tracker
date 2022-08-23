package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Helper Class for Concrete Model to create static Trophy Table
 */
class TrophyTable {
    private static final String TROPHY_TABLE_SQL =
            "CREATE TABLE Trophies (\n" +
                    "Trophy_ID VARCHAR(255) PRIMARY KEY,\n" +
                    "Trophy_Won BOOLEAN DEFAULT false NOT NULL,\n" +
                    "Start_Date DATE NOT NULL,\n" +
                    "Trophy_Message VARCHAR(255) NOT NULL\n" +
                    ");";

    private static final String POPULATE_TROPHY_TABLE =
            "INSERT INTO Trophies (Trophy_ID, Start_Date, Trophy_Message)\n" +
                    "VALUES\n" +
                    "(\'HalfDay\', \'%s\', \'You Completed Half of your Daily Habits\')," +
                    "(\'FullDay\', \'%s\', \'You Completed All of your Daily Habits\')," +
                    "(\'ThreeDay\', \'%s\', \'You Completed your Daily Habits for Three Days this Week\')," +
                    "(\'FiveDay\', \'%s\', \'You Completed your Daily Habits for Five Days this Week\')," +
                    "(\'FullWeek\', \'%s\', \'You Completed your Daily Habits for the Week\')," +
                    "(\'TenDay\', \'%s\', \'You Completed your Daily Habits for Ten Days this Month\')," +
                    "(\'TwentyDay\', \'%s\', \'You Completed your Daily Habits for Twenty Days this Month\')," +
                    "(\'FullMonth\', \'%s\', \'You Completed your Daily Habits for Thirty Days this Month\');";



    public static void createTrophyTable(Connection connection)  {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(TROPHY_TABLE_SQL);
            String today = LocalDate.now().toString();
            String firstOfWeek = LocalDate.now().with(DayOfWeek.MONDAY).toString();
            String firstOfMonth = LocalDate.now().withDayOfMonth(1).toString();
            String populate = String.format(POPULATE_TROPHY_TABLE, today, today, firstOfWeek, firstOfWeek, firstOfWeek, firstOfMonth, firstOfMonth, firstOfMonth);
            stmt.execute(populate);

        } catch (SQLException e) {
            System.err.println("Error on creating Trophy Table");
            e.printStackTrace();
        }
    }

    public static void getDates()  {

    }



}
