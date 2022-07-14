package twm.habit_tracker.controller;

import twm.habit_tracker.model.Model_version_1;
import twm.habit_tracker.view.Habit;
import twm.habit_tracker.view.View;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConcreteController implements Controller {
    private final View view;
    private final Model_version_1 model;

    public ConcreteController(View view, Model_version_1 model) throws SQLException {
        this.view = view;
        this.model = model;
        // Link View to Model
        this.view.setCreateHabitListener(e -> {
            try {
            Habit newHabit = view.getHabitInfo();
            ResultSet rs = model.addHabit(newHabit.habitName(), newHabit.binaryHabit(), newHabit.habitQuestion());
            view.addHabit(rs);
            } catch (SQLException ex) {
            }
        });
        refreshData();
    }

    @Override
    public void refreshData() throws SQLException {
        this.view.setHabitsTableData(model.getHabitData().get());
    }
}
