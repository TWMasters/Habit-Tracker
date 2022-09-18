package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO: 11/08/2022 Merge habit and goal table state using an abstract class!

/**
 * Class for manipulating Goals Table
 */
public class GoalTableState implements TableState {
    private final int INCREMENT = 1;
    private final String ADD_ROW = "INSERT INTO Goals (Goal_ID, Goal_Name, Goal_Description, Deadline) VALUES(%d, \'%s\', %s, %s)";
    private final String DELETE_ROW = "DELETE FROM Goals WHERE Goal_ID = %s;";
    private final String EDIT_ROW = "UPDATE Goals " +
            "SET Goal_Name = \'%s\', Goal_Description = %s, Deadline = %s, Achieved = %s " +
            "WHERE Goal_ID = %s;";
    private final String GET_KEY = "SELECT MAX(Goal_ID) FROM Goals;";
    private final String GET_ROW = "SELECT * FROM Goals where Goal_ID = %s;";
    private final String GET_TABLE = "SELECT * FROM Goals ORDER BY Deadline;";

    Connection context;

    @Override
    public String addEntry(String[] values) {
        try {
            // Get Key
            Statement stmt = context.createStatement();
            ResultSet rs = stmt.executeQuery(GET_KEY);
            rs.next();
            int newKey = rs.getInt(1) + INCREMENT;
            // Add Row
            values[1] = TableStateHelper.editUnitIfNotNull(values[1]);
            values[2] = TableStateHelper.editUnitIfNotNull(values[2]);
            stmt.execute(String.format(ADD_ROW, newKey, values[0], values[1], values[2]));
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
    public String editEntry(String[] values, String lookupValue) {
        try {
            Statement stmt = context.createStatement();
            values[1] = TableStateHelper.editUnitIfNotNull(values[1]);
            values[2] = TableStateHelper.editUnitIfNotNull(values[2]);
            System.out.println(String.format(EDIT_ROW, values[0], values[1], values[2], values[3], lookupValue));
            stmt.execute(String.format(EDIT_ROW, values[0], values[1], values[2], values[3], lookupValue));
        }
        catch (SQLException e) {
            System.err.println("SQL Error on Edit Method");
            e.printStackTrace();
        }
        return null;

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
        return "Goal Table State";
    }

    @Override
    public void setContext(Connection context) {
        this.context = context;
    }
}
