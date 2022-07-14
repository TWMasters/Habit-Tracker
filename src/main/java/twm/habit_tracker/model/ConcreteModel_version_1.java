package twm.habit_tracker.model;

import java.io.File;
import java.sql.*;
import java.util.Optional;

public class ConcreteModel_version_1 implements Model_version_1 {
    private static Model_version_1 model = null; // Singleton
    // Transfer SQL constants to a file?
    private static final String HABIT_TABLE_SQL =
            "CREATE TABLE Habits (\n" +
                    "  Habit_ID  INT PRIMARY KEY,\n" +
                    "  Habit_Name VARCHAR(255) NOT NULL,\n" +
                    "  Binary_Habit BOOLEAN NOT NULL,\n" +
                    "  Habit_Question VARCHAR(255) NOT NULL,\n" +
                    "  Unit VARCHAR(255),\n" +
                    "  Target NUMERIC(18,2)\n" +
                    ");";
    private static final String ADD_HABIT =
            "INSERT INTO Habits" +
                    "(Habit_ID, Habit_Name, Binary_Habit, Habit_Question)" +
                    "VALUES (%d, \'%s\', true, \'%s\')";
    private static final String SELECT_ALL =
            "SELECT Habit_Name, Habit_Question, Unit, Target FROM Habits;";
    private static final String HIGHEST_ID =
            "SELECT MAX(HABIT_ID) FROM Habits";

    private static final String DB_FILEPATH = "db/Habits.mv.db";
    private static final String H2_URL = "jdbc:h2:./db/Habits";

    private Connection connection = null;


    @Override
    public ResultSet addHabit(String habitName, Boolean binaryHabit, String habitQuestion) throws SQLException {
        // Get Highest ID
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(HIGHEST_ID);
        rs.next();
        int newID = rs.getInt(1) + 1;

        // Execute statement
        String addStatement = String.format(ADD_HABIT, newID, habitName, habitQuestion);
        stmt = connection.createStatement();
        stmt.execute(addStatement);

        // Return new entry
        stmt = connection.createStatement();
        return stmt.executeQuery(SELECT_ALL + "WHERE Habit_ID = " + newID);
    }

    private ConcreteModel_version_1() {
        try {
            Boolean dbExists = new File(DB_FILEPATH).exists();
            connection = DriverManager.getConnection(H2_URL);
            if (!dbExists)
                createTables();
        } catch (SQLException e) {
            System.err.println("SQL Exception");
            e.printStackTrace();
        }
    }

    /**
     * Run SQL Command to create Habit Table and commit
     * @throws SQLException
     */
    private void createTables() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(HABIT_TABLE_SQL);
    }

    /**
     * Singleton pattern so no more than one instance of Database
     * Please make getModel static and Constructor private on implementation
     * @return concrete instance of Model
     */
    public static Model_version_1 getModel() {
        if (model == null)
            model = new ConcreteModel_version_1();
        return model;
    }

    @Override
    public Optional<ResultSet> getHabitData() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery(SELECT_ALL);
            return Optional.ofNullable(results);
        }
        catch (SQLException e) {
            System.err.println("SQL Exception");
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
