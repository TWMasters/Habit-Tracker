package twm.habit_tracker.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.Habit;
import twm.habit_tracker.view.View;
import twm.habit_tracker.view.editPages.EditPage;
import twm.habit_tracker.view.editPages.HabitInputFieldsController;
import twm.habit_tracker.view.mainPages.HabitPageController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;
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

        setEditPageMethods();

    }

    @Override
    public void setEditPageMethods() throws SQLException {
        // setTargetTables
        Runnable habitTableTarget = () -> model.changeTargetTable(new HabitTableState());
        HabitInputFieldsController.setTargetTable(habitTableTarget);

        // AddHabit
        Consumer<String[]> addEntryConsumer = (s) -> {
            String[] output = {s[0], "true", s[1], "null", "null"};
            model.addEntry(output);
        };
        EditPage.setAddEntryConsumer(addEntryConsumer);
    }


    @Override
    public void setGoalPageMethods() throws SQLException {

    }

    @Override
    public void setHabitPageMethods() throws SQLException {
        // Retrieve single Habit
        Function<String, Habit> getHabitEntryFunction = (id) -> {
            model.changeTargetTable(new HabitTableState());
            ResultSet rs = model.getEntry(id);
            try {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    Habit h = new Habit();
                    h.setPrimaryKey(rs.getString(1));
                    h.setHabit(rs.getString(2));
                    h.setHabitQuestion(rs.getString(4));
                    return h;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        };
        HabitPageController.setGetHabitEntryFunction(getHabitEntryFunction);

        // Retrieve Habit Data as ObservableList
        Supplier<ObservableList<Habit>> habitDataSupplier = () -> {
            model.changeTargetTable(new HabitTableState());
            ResultSet rs = model.getTable();
            ObservableList<Habit> habitData = FXCollections.observableArrayList();
            try {
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        Habit h = new Habit();
                        h.setPrimaryKey(rs.getString(1));
                        h.setHabit(rs.getString(2));
                        h.setHabitQuestion(rs.getString(4));
                        habitData.add(h);
                    }
                }
                else
                    System.out.println("Result Set is empty!");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return habitData;
        };
        HabitPageController.setHabitDataSupplier(habitDataSupplier);
    }

}
