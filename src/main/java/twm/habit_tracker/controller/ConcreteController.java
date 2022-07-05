package twm.habit_tracker.controller;

import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.View;

public class ConcreteController implements Controller {
    private final View view;
    private Model model;

    public ConcreteController(View v) {
        this.view = v;
        this.view.setCreateHabitListener(e -> view.displayMessage("Creating a Habit"));
    }

}
