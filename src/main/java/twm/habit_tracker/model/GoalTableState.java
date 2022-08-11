package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GoalTableState implements TableState {

    private final String GET_ROW = "SELECT * FROM Goals where Goal_ID = %s;";
    private final String GET_TABLE = "SELECT * FROM Goals;";

    Connection context;

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
    public void setContext(Connection context) {
        this.context = context;
    }
}
