package twm.habit_tracker.controller;


import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.View;
import twm.habit_tracker.view.habitPage.HabitPage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class ConcreteController implements Controller {
    private final View view;
    private final Model model;

    public ConcreteController(View view, Model model) throws SQLException {
        this.view = view;
        this.model = model;

        // Link View to Model
        setGoalPageMethods();
        setHabitPageMethods();

    }

    @Override
    public void setGoalPageMethods() throws SQLException {

    }

    @Override
    public void setHabitPageMethods() throws SQLException {
        // Retrieve Habit Data
        Supplier<ResultSet> s = () -> {
            model.changeTargetTable(new HabitTableState());
            return model.getTable();
        };
        HabitPage.setGetHabitData(s);
    }
}
