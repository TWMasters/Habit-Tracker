package twm.habit_tracker.model.reward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

// TODO: 24/08/2022 Create tests!
/**
 * Helper Class for Concrete Model to create static Trophy Table
 */
class TrophyTable {
    private static final String TROPHY_TABLE_SQL =
            "CREATE TABLE Trophies (\n" +
                    "Trophy_ID VARCHAR(255) PRIMARY KEY,\n" +
                    "Trophy_Won BOOLEAN DEFAULT false NOT NULL,\n" +
                    "Start_Date DATE NOT NULL,\n" +
                    "Trophy_Message VARCHAR(255) NOT NULL,\n" +
                    "Reward INT NOT NULL\n" +
                    ");";

    private static final String POPULATE_TROPHY_TABLE =
            "INSERT INTO Trophies (Trophy_ID, Start_Date, Trophy_Message, Reward)\n" +
                    "VALUES\n" +
                    "(\'HalfDay\', \'%s\', \'You Completed Half of your Daily Habits\', 8)," +
                    "(\'FullDay\', \'%s\', \'You Completed All of your Daily Habits\', 12)," +
                    "(\'ThreeDay\', \'%s\', \'You Completed your Daily Habits for Three Days this Week\', 25)," +
                    "(\'FiveDay\', \'%s\', \'You Completed your Daily Habits for Five Days this Week\', 60)," +
                    "(\'FullWeek\', \'%s\', \'You Completed your Daily Habits for the Week\', 90)," +
                    "(\'TenDay\', \'%s\', \'You Completed your Daily Habits for Ten Days this Month\', 100)," +
                    "(\'TwentyDay\', \'%s\', \'You Completed your Daily Habits for Twenty Days this Month\', 250)," +
                    "(\'FullMonth\', \'%s\', \'You Completed your Daily Habits for Thirty Days this Month\', 350);";

    private static final String GET_AWARDED_TROPHIES =
            "SELECT * FROM Trophies WHERE Trophy_ID in (%s);";

    private static final String GET_DATE_RANGE =
            "SELECT * FROM Habit_Tracker WHERE Date >= \'%s\' AND DATE <= \'%s\';";

    private static final String GET_ROW =
            "SELECT * FROM Trophies WHERE Trophy_ID = \'%s\';";

    private static final String GET_TABLE =
            "SELECT * FROM Trophies;";

    private static final String RESET_ROW =
            "UPDATE Trophies SET Trophy_Won = 0, Start_Date = \'%s\' WHERE Trophy_ID = \'%s\';";

    private static final String WIN_TROPHY =
            "UPDATE Trophies SET Trophy_Won = 1 WHERE Trophy_ID = \'%s\';";

    private final Connection connection;

    public TrophyTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Helper method for checking whether Weekly or Monthly trophies must be rewarded
     * @param input String to be updated and returned to Client
     * @param trophy Lookup Trophy in Database
     * @param count Number of successful days to check against target
     * @param target
     * @throws SQLException
     */
    private void checkWeekOrMonth(ArrayList<String> input, String trophy, int count, int target) throws
            SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsTrophy = stmt.executeQuery(String.format(GET_ROW, trophy));
        rsTrophy.next();
        if ( count >= target && !rsTrophy.getBoolean(2)) {
            input.add(rsTrophy.getString(4));
            stmt.executeUpdate(String.format(WIN_TROPHY, "\'" + trophy + "\'"));
        }

    }

    // TODO: 24/08/2022 Split into daily, weekly, and monthly checks!

    /**
     * Check whether any trophies have been rewarded
     * @return String of messages to show User
     */
    public Optional<ResultSet> checkAwards() {
        Optional<ResultSet> rsOutput = Optional.empty();
        ArrayList<String> output = new ArrayList<>();
        String[] dates = getDates();
        try {
            Statement stmt = connection.createStatement();
            // Date Info
            ResultSet rsToday = stmt.executeQuery(String.format(GET_DATE_RANGE, dates[0], dates[0]));
            rsToday.next();
            int target = rsToday.getInt(2);
            int achieved = rsToday.getString(3).split("=1").length;

            // Half Day
            ResultSet rsHalfDay = stmt.executeQuery(String.format(GET_ROW, "HalfDay"));
            rsHalfDay.next();
            if ( target >= 4 && !rsHalfDay.getBoolean(2) && achieved >= (target / 2) ) {
                output.add(rsHalfDay.getString(4));
                stmt.executeUpdate(String.format(WIN_TROPHY, "\'HalfDay\'"));
            }

            // Full Day
            ResultSet rsFullDay = stmt.executeQuery(String.format(GET_ROW, "FullDay"));
            rsFullDay.next();
            if ( target >= 4 && !rsFullDay.getBoolean(2) && achieved == (target) ) {
                output.add(rsFullDay.getString(4));
                stmt.executeUpdate(String.format(WIN_TROPHY, "\'FullDay\'"));
            }

            // Week Info
            ResultSet rsWeek = stmt.executeQuery(String.format(GET_DATE_RANGE, dates[1], dates[3]));
            int count = 0;
            while (rsWeek.next()) {
                target = rsWeek.getInt(2);
                achieved = rsWeek.getString(3).split("=1").length;
                if ( target >= 4 && achieved == target)
                    count++;
            }

            checkWeekOrMonth(output, "ThreeDay", count, 3);
            checkWeekOrMonth(output, "FiveDay", count, 5);
            checkWeekOrMonth(output, "FullWeek", count, 7);

            // Month Info
            ResultSet rsMonth = stmt.executeQuery(String.format(GET_DATE_RANGE, dates[2], dates[4]));
            count = 0;
            while (rsMonth.next()) {
                target = rsMonth.getInt(2);
                achieved = rsMonth.getString(3).split("=1").length;
                if ( target >= 4 && achieved == target)
                    count++;
            }

            checkWeekOrMonth(output, "TenDay", count, 10);
            checkWeekOrMonth(output, "TwentyDay", count, 20);
            checkWeekOrMonth(output, "FullMonth", count, LocalDate.now().lengthOfMonth());

            // Generate ResultSet
            rsOutput = Optional.of(stmt.executeQuery(String.format(GET_AWARDED_TROPHIES, String.join(", ", output))));
            return rsOutput;

        }
        catch (SQLException e) {
            System.out.println("Error on checking for awards");
            e.printStackTrace();
        }
        return rsOutput;
    }

    /**
     * Run SQL Command to create Trophy Table
     */
    public void createTrophyTable()  {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(TROPHY_TABLE_SQL);
            String[] dates = getDates();
            String populate = String.format(POPULATE_TROPHY_TABLE, dates[0], dates[0], dates[1], dates[1], dates[1], dates[2], dates[2], dates[2]);
            stmt.execute(populate);

        } catch (SQLException e) {
            System.err.println("Error on creating Trophy Table");
            e.printStackTrace();
        }
    }

    /**
     * Helper method to get dates used in Trophy Table
     * @return Array of relevant dates in String format
     */
    private String[] getDates()  {
        String today = LocalDate.now().toString();
        String firstOfWeek = LocalDate.now().with(DayOfWeek.MONDAY).toString();
        String firstOfMonth = LocalDate.now().withDayOfMonth(1).toString();
        String endOfWeek = LocalDate.now().with(DayOfWeek.SUNDAY).toString();
        int monthEnd = LocalDate.now().lengthOfMonth();
        String endOfMonth = LocalDate.now().withDayOfMonth(monthEnd).toString();
        return new String[] {today, firstOfWeek, firstOfMonth, endOfWeek,  endOfMonth};

    }

    /**
     * Get Copy of trophy table
     * @return result set of trophy table
     */
    public ResultSet getTrophyTable() {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(GET_TABLE);
        } catch (SQLException e) {
            System.err.println("Error when getting trophy table");
            e.printStackTrace();
        }
        return null;
    }

    // TODO: 24/08/2022 Reduce Repetition
    /**
     * Update and reset rows in Trophy Table where necessary
     * when program is run
     */
    public void updateDates() {
        System.out.println("Updating Trophy Table");
        String[] dates = getDates();
        try {
            Statement stmt = connection.createStatement();
            // Update Day
            ResultSet rs = stmt.executeQuery(String.format(GET_ROW, "FullDay"));
            rs.next();
            String oldDate = rs.getString(3);
            if (!oldDate.equals(dates[0])) {
                stmt.executeUpdate(String.format(RESET_ROW, dates[0], "HalfDay" ));
                stmt.executeUpdate(String.format(RESET_ROW, dates[0], "FullDay" ));
            }
            // Update Week
            rs = stmt.executeQuery(String.format(GET_ROW, "FullWeek"));
            rs.next();
            oldDate = rs.getString(3);
            if (!oldDate.equals(dates[1])) {
                stmt.executeUpdate(String.format(RESET_ROW, dates[1], "ThreeDay"));
                stmt.executeUpdate(String.format(RESET_ROW, dates[1], "FiveDay"));
                stmt.executeUpdate(String.format(RESET_ROW, dates[1], "FullWeek"));
            }
            // Update Month
            rs = stmt.executeQuery(String.format(GET_ROW, "FullMonth"));
            rs.next();
            oldDate = rs.getString(3);
            if (!oldDate.equals(dates[2])) {
                stmt.executeUpdate(String.format(RESET_ROW, dates[2], "TenDay"));
                stmt.executeUpdate(String.format(RESET_ROW, dates[2], "TwentyDay"));
                stmt.executeUpdate(String.format(RESET_ROW, dates[2], "FullMonth"));
            }

        }
        catch (SQLException e) {
            System.err.println("Error on Updating Trophy Table");
            e.printStackTrace();
        }

    }

}
