package twm.habit_tracker.model;

import java.util.ArrayList;

public interface TrophyModel {

    /**
     * Check if any awards won on ticking off habit
     * @return Array of Award Messages
     */
    public ArrayList<String> checkAwards();
}
