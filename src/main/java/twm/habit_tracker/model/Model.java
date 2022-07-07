package twm.habit_tracker.model;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * Model creates a connection to the Habit Tracker Database,
 * redirects commands from the Controller class to the relevant Table,
 * and supplies the Controller data on demand
 */
public interface Model {
    /**
     * Use to get data from Habits Relation
     * @return Habits ResultSet
     */
    Optional<ResultSet> getHabitData();
}

