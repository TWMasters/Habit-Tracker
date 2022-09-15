package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for manipulating Habits Table
 */
public class HabitTableState implements TableState {

    private final int HABIT_NAME = 0;
    private final int BINARY_HABIT = 1;
    private final int HABIT_QUESTION = 2;
    private final int UNIT = 3;
    private final int TARGET = 4;
    private final int HABIT_ID_COLUMN_NO = 1;
    private final int INCREMENT = 1;
    private final String ADD_ROW = "INSERT INTO Habits VALUES(%d, \'%s\', %s, \'%s\', %s, %s)";
    private final String DELETE_ROW = "DELETE FROM Habits WHERE Habit_ID = %s;";
    private final String EDIT_ROW = "UPDATE Habits " +
            "SET Habit_Name = \'%s\', Binary_Habit = %s, Habit_Question = \'%s\', Unit = %s, Target = %s " +
            "WHERE HABIT_ID = %s;";
    private final String GET_KEY = "SELECT MAX(Habit_ID) FROM Habits;";
    private final String GET_ROW = "SELECT * FROM Habits WHERE Habit_ID = %s;";
    private final String GET_TABLE = "SELECT * FROM Habits;";

    Connection context;

    @Override
    public String addEntry(String[] values) {
        try {
            // Get Key
            Statement stmt = context.createStatement();
            ResultSet rs = stmt.executeQuery(GET_KEY);
            rs.next();
            int newKey = rs.getInt(HABIT_ID_COLUMN_NO) + INCREMENT;
            // Add Row
            values[UNIT] = TableStateHelper.editUnitIfNotNull(values[UNIT]);
            stmt.execute(String.format(ADD_ROW,
                    newKey,
                    values[HABIT_NAME],
                    values[BINARY_HABIT],
                    values[HABIT_QUESTION],
                    values[UNIT],
                    values[TARGET]));
            return String.valueOf(newKey);

        } catch (SQLException e) {
            System.err.println("SQL Error on Add Method");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteEntry(String lookupValue) {
        try {
            Statement stmt = context.createStatement();
            stmt.execute(String.format(DELETE_ROW, lookupValue));
        } catch (SQLException e) {
            System.err.println("SQL Error on Delete Method");
            e.printStackTrace();
        }

    }

    @Override
    public void editEntry(String[] values, String lookupValue) {
        try {
            Statement stmt = context.createStatement();
            values[3] = TableStateHelper.editUnitIfNotNull(values[3]);
            stmt.execute(String.format(EDIT_ROW, values[0], values[1], values[2], values[3], values[4], lookupValue));
        }
        catch (SQLException e) {
            System.err.println("SQL Error on Edit Method");
            e.printStackTrace();
        }

    }

    @Override
    public ResultSet getEntry(String lookupValue) {
        try {
            Statement stmt = context.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(GET_ROW, lookupValue));
            return rs;
        } catch (SQLException e) {
            System.err.println("SQL Error on Get Entry Method");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet getTable() {
        try {
            Statement stmt = context.createStatement();
            ResultSet rs = stmt.executeQuery(GET_TABLE);
            return rs;

        } catch (SQLException e) {
            System.err.println("SQL Error on Get Table Method");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getTableState() {
        return "Habit Table State";
    }

    @Override
    public void setContext(Connection context) {
        this.context = context;
    }

}
