package twm.habit_tracker.model;

import twm.habit_tracker.model.reward.ConcreteRewardManager;
import twm.habit_tracker.model.reward.RewardManager;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class ConcreteModel implements Model {

    private static final String DB_FILEPATH = "db/Habits.mv.db";
    private static final String H2_URL = "jdbc:h2:./db/Habits";
    // TODO: 14/07/2022 Transfer SQL to a separate file
    private static final String HABIT_TABLE_SQL =
            "CREATE TABLE Habits (\n" +
                    "  Habit_ID  INT PRIMARY KEY,\n" +
                    "  Habit_Name VARCHAR(165) UNIQUE NOT NULL,\n" +
                    "  Binary_Habit BOOLEAN NOT NULL,\n" +
                    "  Habit_Question VARCHAR(165) NOT NULL,\n" +
                    "  Unit VARCHAR(165),\n" +
                    "  Target NUMERIC(18,2)\n" +
                    ");";
    private static final String GOAL_TABLE_SQL = "" +
            "CREATE TABLE Goals (\n" +
                    "  Goal_ID INT PRIMARY KEY,\n" +
                    "  Goal_Name VARCHAR(165) NOT NULL,\n" +
                    "  Goal_Description VARCHAR(1000),\n" +
                    "  Deadline DATE NOT NULL,\n" +
                    "  Achieved BOOLEAN DEFAULT false NOT NULL\n" +
                    ");";
    private static final String HABIT_TRACKER_TABLE =
            "CREATE TABLE Habit_Tracker (\n" +
                    "Date DATE PRIMARY KEY,\n" +
                    "Target INT DEFAULT 0 NOT NULL,\n" +
                    "Completed VARCHAR(250) DEFAULT '' NOT NULL\n" +
                    ");";

    private Connection connection = null;
    private static Model model = null;
    private TableState targetTable;
    private RewardManager rewardManager;

    private ConcreteModel() {
        try {
            Boolean databaseExists = new File(DB_FILEPATH).exists();
            connection = DriverManager.getConnection(H2_URL);
            rewardManager = new ConcreteRewardManager(connection);
            if (!databaseExists)
                createTables();
            else
                updateTables();
        } catch (SQLException e) {
            System.err.println("SQL Exception on Connection");
        }
    }

    @Override
    public void changeTargetTable(TableState newTargetTable) {
        this.targetTable = newTargetTable;
        targetTable.setContext(connection);
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("SQL Exception while closing connection");
        }
    }

    /**
     * Helper method to populate a new Habits database with following Relations:
     *  - Reward Tables
     *  - Habit
     *  - Goals
     *  - HabitTracker
     */
    private void createTables() {
        try {
            rewardManager.buildTables();
            Statement stmt = connection.createStatement();
            stmt.execute(HABIT_TABLE_SQL);
            stmt.execute(GOAL_TABLE_SQL);
            stmt.execute(HABIT_TRACKER_TABLE);
            populateHabitTrackerTable();
        }
        catch (SQLException e) {
            System.err.println("SQL Exception on Creating Tables");
            e.printStackTrace();
        }
    }

    /**
     * Singleton pattern so no more than one instance of Database
     * Please make getModel static and Constructor private on implementation
     * @return concrete instance of Model
     */
    public static Model getModel() {
        if (model == null)
            model = new ConcreteModel();
        return model;
    }

    @Override
    public ResultSet getTable() {
        return targetTable.getTable();
    }

    @Override
    public String getTableState() {
        return targetTable.getTableState();
    }

    @Override
    public ResultSet getEntry(String lookupValue) {
        return targetTable.getEntry(lookupValue);
    }

    @Override
    public String addEntry(String[] values) {
        return targetTable.addEntry(values);
    }

    @Override
    public void deleteEntry(String lookupValue) {
        targetTable.deleteEntry(lookupValue);
    }

    @Override
    public void editEntry(String[] values, String lookupValue) {
        targetTable.editEntry(values, lookupValue);
    }

    @Override
    public RewardManager getRewardManager() {
        return rewardManager;
    }


    /**
     * Helper method to populate Habit Tracker Table
     * @throws SQLException
     */
    private void populateHabitTrackerTable() throws SQLException {
        changeTargetTable(new HabitTrackerTableState());
        LocalDate dateToday = LocalDate.now();
        LocalDate startOfMo = dateToday.withDayOfMonth(1);
        LocalDate endOfMo = dateToday.withDayOfMonth(1).plusMonths(2);
        startOfMo.datesUntil(endOfMo)
                .forEach(d -> {
                    String[] input = {d.toString()};
                    targetTable.addEntry(input);
                });
    }

    /**
     * Helper method to update date-dependant tables
     * - i.e. Trophy and HabitTracker tables -
     * on booting up application
     */
    private void updateTables() {
        rewardManager.updateTables();
        changeTargetTable(new HabitTrackerTableState());
        LocalDate checkDate = LocalDate.now().withDayOfMonth(1).plusMonths(1);
        ResultSet rs = getEntry(checkDate.toString());
        try {
            if (rs.isBeforeFirst())
                return;
            else
                checkDate.datesUntil(checkDate.plusMonths(1))
                        .forEach(d -> {
                            String[] input = {d.toString()};
                            targetTable.addEntry(input);
                        });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
