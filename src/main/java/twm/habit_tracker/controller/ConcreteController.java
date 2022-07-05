package twm.habit_tracker.controller;

import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.View;

public class ConcreteController implements Controller {
    private final View view;
    private Model model;

    public ConcreteController(View view) {
        this.view = view;
    }

    @Override
    public void render() {
        view.run();
    }
}
