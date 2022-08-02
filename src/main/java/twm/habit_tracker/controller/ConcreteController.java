package twm.habit_tracker.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.Habit;
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
        // Retrieve Habit Data as ObservableList
        Supplier<ObservableList<Habit>> s = () -> {
            model.changeTargetTable(new HabitTableState());
            ResultSet rs = model.getTable();
            ObservableList<Habit> list = FXCollections.observableArrayList();
            try {
                while (rs.next()) {
                    Habit h = new Habit();
                    h.setHabit(rs.getString(2));
                    h.setHabitQuestion(rs.getString(3));
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        };
        HabitPage.setGetHabitData(s);
    }

}
