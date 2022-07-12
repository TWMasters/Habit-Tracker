package twm.habit_tracker.controller;

import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.View;

import java.sql.SQLException;

public class ConcreteController implements Controller {
    private final View view;
    private final Model model;

    public ConcreteController(View view, Model model) throws SQLException {
        this.view = view;
        this.model = model;
        // Link View to Model
        this.view.setCreateHabitListener(e -> view.displayMessage("Alert!","Do you wish to create a new Habit?"));
        this.view.setHabitsTableData(model.getHabitData().get());
    }
}
