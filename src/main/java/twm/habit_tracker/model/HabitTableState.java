package twm.habit_tracker.model;

import java.sql.Connection;
import java.sql.ResultSet;

public class HabitTableState implements TableState {
    Connection context;

    @Override
    public ResultSet getTable() {
        return null;
    }

    @Override
    public ResultSet getRow(String lookupValue) {
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
