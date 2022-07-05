package twm.habit_tracker.user_agent;


import javafx.application.Application;
import twm.habit_tracker.view.GUIView;
import twm.habit_tracker.view.View;


public class Main {
    public static void main(String[] args) {
        View GUI = new GUIView();
        GUI.run();
    }
}
