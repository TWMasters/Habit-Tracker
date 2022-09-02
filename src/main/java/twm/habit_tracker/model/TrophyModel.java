package twm.habit_tracker.model;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface TrophyModel {

    /**
     * Check if any awards won on ticking off habit
     * @return Array of Award Messages
     */
    ArrayList<String> checkAwards();

    /**
     * Get current state of trophies
     * Return ResultSet of Trophy Table
     */
    ResultSet getTrophyTable();
}
