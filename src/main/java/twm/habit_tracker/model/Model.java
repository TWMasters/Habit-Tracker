package twm.habit_tracker.model;

import java.sql.ResultSet;

public interface Model {
    /**
     * Use to get data from Habits Relation
     * @return Habits ResultSet
     */
    ResultSet getHabitData();
}

