package twm.habit_tracker.controller;

import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.View;

import java.sql.SQLException;

public class ConcreteController implements Controller {
    private final View view;
    private Model model;

    public ConcreteController(View v, Model m) throws SQLException {
        this.view = v;
        this.model = m;
        this.view.setCreateHabitListener(e -> view.displayMessage("Alert!","Creating a Habit"));
        this.view.setHabitsTableData(model.getHabitData().get());
    }
}
