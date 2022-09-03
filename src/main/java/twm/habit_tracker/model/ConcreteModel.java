package twm.habit_tracker.model;

import twm.habit_tracker.model.reward.ConcreteRewardModel;
import twm.habit_tracker.model.reward.RewardModel;

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
                    "  Habit_Name VARCHAR(255) NOT NULL,\n" +
                    "  Binary_Habit BOOLEAN NOT NULL,\n" +
                    "  Habit_Question VARCHAR(255) NOT NULL,\n" +
                    "  Unit VARCHAR(255),\n" +
                    "  Target NUMERIC(18,2)\n" +
                    ");";
    private static final String GOAL_TABLE_SQL = "" +
            "CREATE TABLE Goals (\n" +
                    "  Goal_ID INT PRIMARY KEY,\n" +
                    "  Goal_Name VARCHAR(255) NOT NULL,\n" +
                    "  Goal_Description VARCHAR(255),\n" +
                    "  Deadline DATE,\n" +
                    "  Achieved BOOLEAN DEFAULT false NOT NULL\n" +
                    ");";
    private static final String HABIT_TRACKER_TABLE =
            "CREATE TABLE Habit_Tracker (\n" +
                    "Date DATE PRIMARY KEY,\n" +
                    "Target INT DEFAULT 0 NOT NULL,\n" +
                    "Completed VARCHAR(255) DEFAULT '' NOT NULL\n" +
                    ");";

    private Connection connection = null;
    private static Model model = null;
    private TableState targetTable;
    private RewardModel rewardModel;

    private ConcreteModel() {
        try {
            Boolean databaseExists = new File(DB_FILEPATH).exists();
            connection = DriverManager.getConnection(H2_URL);
            rewardModel = new ConcreteRewardModel(connection);
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
     *  - Habit
     *  - HabitTracker
     */
    private void createTables() {
        try {
            Statement stmt = connection.createStatement();

            rewardModel.buildTables();

            stmt.execute(HABIT_TABLE_SQL);
            stmt.execute(GOAL_TABLE_SQL);

            stmt.execute(HABIT_TRACKER_TABLE);

            // TODO: 15/08/2022 Build date table -> Move to a different method?
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
    public RewardModel getRewardModel() {
        return rewardModel;
    }

    /**
     * Helper method to update tables on booting up application
     */
    private void updateTables() {
        System.out.println("Updating Tables!");

        // Trophies
        rewardModel.updateTables();

        // Dates
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
