package twm.habit_tracker.user_agent;

import twm.habit_tracker.controller.ConcreteController;
import twm.habit_tracker.controller.Controller;
import twm.habit_tracker.view.GUIView;
import twm.habit_tracker.view.View;


public class Main {
    public static void main(String[] args) {
        View GUI = new GUIView();
        Controller controller = new ConcreteController(GUI);
        controller.render();
    }
}
