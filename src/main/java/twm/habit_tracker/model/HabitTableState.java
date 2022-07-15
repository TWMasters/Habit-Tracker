package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HabitTableState implements TableState {
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
