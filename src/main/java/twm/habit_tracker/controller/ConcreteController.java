package twm.habit_tracker.controller;

import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.Habit;
import twm.habit_tracker.view.View;

import java.sql.SQLException;

public class ConcreteController implements Controller {
    private final View view;
    private final Model model;

    public ConcreteController(View view, Model model) throws SQLException {
        this.view = view;
        this.model = model;
        // Link View to Model
        this.view.setCreateHabitListener(e -> {
            Habit newHabit = view.getHabitInfo();
            model.addEntry(new String[]{newHabit.habitName(), String.valueOf(newHabit.binaryHabit()), newHabit.habitQuestion()});
            // view.addHabit(rs);
        });
        refreshData();
    }

    @Override
    public void refreshData() throws SQLException {
        model.changeTargetTable(new HabitTableState());
        this.view.setHabitsTableData(model.getTable());
    }
}
