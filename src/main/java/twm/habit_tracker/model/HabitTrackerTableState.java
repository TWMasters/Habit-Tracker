package twm.habit_tracker.model;

import java.sql.*;

public class HabitTrackerTableState implements TableState{
    private final String ADD_ROW = "INSERT INTO Habit_Tracker(Date) VALUES(\'%s\')";
    private final String DELETE_ROW = "DELETE FROM Habit_Tracker WHERE Date = \'%s\';";
    // TODO: 20/07/2022 Come back to Edit_Row!
    private final String EDIT_ROW = "UPDATE Habit_Tracker " +
            "SET Target =  %s" +
            "SET Completed =  \'%s\'" +
            "WHERE Date = \'%s\';";
    private final String GET_ROW = "SELECT * FROM Habit_Tracker WHERE Date = \'%s\';";
    private final String GET_TABLE = "SELECT * FROM Habit_Tracker;";

    Connection context;
    Statement stmt;

    @Override
    public void addEntry(String[] values) {
        try {
            // Add Row -> All other columns aside from Date will be false by default initially
            stmt.execute(String.format(ADD_ROW, values[0]));

        } catch (SQLException e) {
            System.err.println("SQL Error on Add Method");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntry(String lookupValue) {
        try {
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
            stmt.execute(String.format(EDIT_ROW, values[0], values[1], lookupValue));
        } catch (SQLException e) {
            System.err.println("SQL Error on Edit Method");
            e.printStackTrace();
        }

    }

    @Override
    public ResultSet getEntry(String lookupValue) {
        try {
            ResultSet rs = stmt.executeQuery(String.format(GET_ROW, lookupValue));
            return rs;
        } catch (SQLException e) {
            System.err.println("Error on Get Entry Method");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet getTable() {
        try {
            ResultSet rs = stmt.executeQuery(GET_TABLE);
            return rs;
        } catch (SQLException e) {
            System.err.println("Error on Get Table Method");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getTableState() {
        return "Habit Tracker State";
    }

    @Override
    public void setContext(Connection context) {
        this.context = context;

        try {
            this.stmt = context.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
