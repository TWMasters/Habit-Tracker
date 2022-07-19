package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HabitTableState implements TableState {
    private final int INCREMENT = 1;
    private final String ADD_ROW = "INSERT INTO Habits VALUES(%d, \'%s\', %s, \'%s\', %s, %s)";
    private final String GET_KEY = "SELECT MAX(Habit_ID) FROM Habits;";
    private final String GET_ROW = "SELECT * FROM Habits WHERE Habit_ID = %s;";

    Connection context;

    @Override
    public ResultSet getTable() {
        return null;
    }

    @Override
    public ResultSet getRow(String lookupValue) {
        try {
            Statement stmt = context.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(GET_ROW, lookupValue));
            return rs;
        } catch (SQLException e) {
            System.err.println("SQL Error on Get Method");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addEntry(String[] values) {
        try {
            // Get Key
            Statement stmt = context.createStatement();
            ResultSet rs = stmt.executeQuery(GET_KEY);
            rs.next();
            int newKey = rs.getInt(1) + INCREMENT;
            // Add Row
            stmt.execute(String.format(ADD_ROW, newKey, values[0], values[1], values[2], values[3], values[4]));

        } catch (SQLException e) {
            System.err.println("SQL ERROR in Add method");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntry(String lookupValue) {

    }

    @Override
    public void editEntry(String[] values, String lookupValue) {

    }

    @Override
    public void setContext(Connection context) {
        this.context = context;
    }
}
