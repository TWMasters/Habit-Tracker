package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HabitTableState implements TableState {
    private final int INCREMENT = 1;
    private final String ADD_ROW = "INSERT INTO Habits VALUES(%d, \'%s\', %s, \'%s\', %s, %s)";
    private final String DELETE_ROW = "DELETE FROM Habits WHERE Habit_ID = %s;";
    private final String EDIT_ROW = "UPDATE Habits " +
            "SET Habit_Name = \'%s\', Binary_Habit = %s, Habit_Question = \'%s\', Unit = %s, Target = %s " +
            "WHERE HABIT_ID = %s;";
    private final String GET_KEY = "SELECT MAX(Habit_ID) FROM Habits;";
    private final String GET_ROW = "SELECT * FROM Habits WHERE Habit_ID = %s;";
    private final String NULL_STRING = "null";

    Connection context;

    @Override
    public void addEntry(String[] values) {
        try {
            // Get Key
            Statement stmt = context.createStatement();
            ResultSet rs = stmt.executeQuery(GET_KEY);
            rs.next();
            int newKey = rs.getInt(1) + INCREMENT;
            // Add Row
            values[3] = editUnitIfNotNull(values[3]);
            stmt.execute(String.format(ADD_ROW, newKey, values[0], values[1], values[2], values[3], values[4]));

        } catch (SQLException e) {
            System.err.println("SQL Error on Add Method");
            e.printStackTrace();
        }
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
            values[3] = editUnitIfNotNull(values[3]);
            stmt.execute(String.format(EDIT_ROW, values[0], values[1], values[2], values[3], values[4], lookupValue));
        }
        catch (SQLException e) {
            System.err.println("SQL Error on Edit Method");
            e.printStackTrace();
        }

    }

    /**
     * Helper method for converting non-null Unit Values into correct format
     * @param input Raw Unit Value
     * @return Formatted Unit Value
     */
    private String editUnitIfNotNull(String input) {
        String output = input.equals(NULL_STRING) ? input : "\'" + input + "\'";
        return output;
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
        } catch (SQLException e) {
            System.err.println("SQL Error on Get Table Method");
        }

        return null;
    }

    @Override
    public void setContext(Connection context) {
        this.context = context;
    }

}
