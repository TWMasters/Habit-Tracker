package twm.habit_tracker.model;

import java.io.File;
import java.sql.*;

public class ConcreteModel implements Model {

    private static final String DB_FILEPATH = "db/Habits.mv.db";
    private static final String H2_URL = "jdbc:h2:./db/Habits";
    // TODO: 14/07/2022 Transfer SQL to a separate file
    private static final String HABIT_TABLE_SQL =
            "CREATE TABLE Habits (\n" +
                    "  Habit_ID  INT PRIMARY KEY,\n" +
                    "  Habit_Name VARCHAR(255) NOT NULL,\n" +
                    "  Binary_Habit BOOLEAN NOT NULL,\n" +
                    "  Habit_Question VARCHAR(255) NOT NULL,\n" +
                    "  Unit VARCHAR(255),\n" +
                    "  Target NUMERIC(18,2)\n" +
                    ");";

    private Connection connection = null;
    private static Model model = null;
    private TableState targetTable;

    private ConcreteModel() {
        try {
            Boolean databaseExists = new File(DB_FILEPATH).exists();
            connection = DriverManager.getConnection(H2_URL);
            if (!databaseExists)
                createTables();
        } catch (SQLException e) {
            System.err.println("SQL Exception on Connection");
        }
    }

    /**
     * Helper method to populate a new Habits database with following Relations:
     *  - Habit
     *  - HabitTracker
     */
    private void createTables() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(HABIT_TABLE_SQL);
        }
        catch (SQLException e) {
            System.err.println("SQL Exception on Creating Tables");
        }
    }

    @Override
    public void changeTargetTable(TableState newTargetTable) {
        this.targetTable = newTargetTable;
    }

    /**
     * Singleton pattern so no more than one instance of Database
     * Please make getModel static and Constructor private on implementation
     * @return concrete instance of Model
     */
    public Model getModel() {
        if (model == null)
            model = new ConcreteModel();
        return model;
    }

    @Override
    public ResultSet getTable() {
        return targetTable.getTable();
    }

    @Override
    public ResultSet getRow(String lookupValue) {
        return targetTable.getRow(lookupValue);
    }

    @Override
    public void addEntry(String[] values) {
        targetTable.addEntry(values);
    }

    @Override
    public void deleteEntry(String lookupValue) {
        targetTable.deleteEntry(lookupValue);
    }

    @Override
    public void editEntry(String[] values, String lookupValue) {
        targetTable.editEntry(values, lookupValue);
    }
}
