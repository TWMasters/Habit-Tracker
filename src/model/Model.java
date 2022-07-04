package model;

import view.View;

/**
 * Model creates a connection to the Habit Tracker Database,
 * redirects commands from the Controller class to the relevant Table,
 * and updates View
 */
public interface Model {

    /**
     * Add View to views array field
     * @param view
     */
    void addView(View view);


    /**
     * Notify all views observing Model
     */
    void notifyViews();
}
