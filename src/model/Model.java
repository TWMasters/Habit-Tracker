package model;

/**
 * Model creates a connection to the Habit Tracker Database,
 * redirects commands from the Controller class to the relevant Table,
 * and updates View
 */
public interface Model {
    /**
     * Update all Views stored in Model
     * @param context
     */
    void notify(String context);
}
