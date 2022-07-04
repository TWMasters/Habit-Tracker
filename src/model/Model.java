package model;

/**
 * Model creates a connection to the Habit Tracker Database,
 * redirects commands from the Controller class to the relevant Table,
 * and updates View
 */
public interface Model {
    /**
     * Singleton pattern so no more than one instance of Database
     * Please make Constructor private on implementation
     * @return concrete instance of Model
     */
    Model getModel();

    /**
     * Update all Views stored in Model
     * @param context
     */
    void notify(String context);
}
