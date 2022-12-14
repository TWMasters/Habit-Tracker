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
    private final String GET_UNIQUE_NAME  = "SELECT * FROM Habits WHERE Habit_Name = \'%s\';";

    Connection context;


    private Boolean inputFieldValidation(String unit, String target) {
        Boolean flag =  true;
        if (unit.equals("null") || target.equals("null"))
            flag = false;
        return flag;
    }

    /**
     * Helper method for checking no habit name repetition
     * @param s
     * @return
     */
    private Boolean uniqueNameValidation(String s) {
        Boolean flag;
        try {
            Statement stmt = context.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(GET_UNIQUE_NAME, s));
            flag = !rs.isBeforeFirst() ? true : false;
            return flag;
        }
        catch (SQLException e) {
            System.err.println("Error while checking for unique name");
        }
        return null;
    };

    @Override
    public String addEntry(String[] values) {
        try {
            // Validation Checks
            if (values[HABIT_NAME].equals(""))
                return "!Please enter a Habit Name";
            if (!uniqueNameValidation(values[HABIT_NAME]))
                return "!Please choose a unique Habit name";
            if ( values[BINARY_HABIT].equals("false") && !inputFieldValidation(values[UNIT], values[TARGET]) )
                return "!Unit and Target fields must be completed for Analogue Habits";
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
    public String editEntry(String[] values, String lookupValue) {
        try {
            // Validation Checks
            if (values[HABIT_NAME].equals(""))
                return "!Please enter a Habit Name";
            ResultSet rs = getEntry(lookupValue);
            rs.next();
            String oldName = rs.getString(2);
            if ( !oldName.equals(values[HABIT_NAME]) && !uniqueNameValidation(values[HABIT_NAME]))
                return "!Please choose a unique Habit name";
            if ( values[BINARY_HABIT].equals("false") && !inputFieldValidation(values[UNIT], values[TARGET]) )
                return "!Unit and Target fields must be completed for Analogue Habits";
            Statement stmt = context.createStatement();
            values[3] = TableStateHelper.editUnitIfNotNull(values[3]);
            stmt.execute(String.format(EDIT_ROW, values[0], values[1], values[2], values[3], values[4], lookupValue));
        }
        catch (SQLException e) {
            System.err.println("SQL Error on Edit Method");
            e.printStackTrace();
        }
        return lookupValue;

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
