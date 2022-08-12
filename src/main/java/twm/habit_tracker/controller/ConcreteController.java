package twm.habit_tracker.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twm.habit_tracker.model.GoalTableState;
import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.Goal;
import twm.habit_tracker.view.Habit;
import twm.habit_tracker.view.View;
import twm.habit_tracker.view.editPages.EditPage;
import twm.habit_tracker.view.editPages.HabitInputFieldsController;
import twm.habit_tracker.view.mainPages.GoalPageController;
import twm.habit_tracker.view.mainPages.HabitPageController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
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
        // AddHabit
        Consumer<String[]> addEntryConsumer = (s) -> {
            model.addEntry(s);
        };
        EditPage.setAddEntryConsumer(addEntryConsumer);

        //DeleteHabit
        Consumer<String> deleteEntryConsumer = (s) -> {
            model.deleteEntry(s);
        };
        EditPage.setDeleteEntryConsumer(deleteEntryConsumer);

        //SaveHabit
        BiConsumer<String[], String> editEntryConsumer = (k, s) -> {
            model.editEntry(k,s);
        };
        EditPage.setEditEntryConsumer(editEntryConsumer);
    }


    @Override
    public void setGoalPageMethods() throws SQLException {
        // Retrieve Goal Data as ObservableList
        Supplier<ObservableList<Goal>> goalDataSupplier = () -> {
            model.changeTargetTable(new GoalTableState());
            ResultSet rs = model.getTable();
            ObservableList<Goal> goalData = FXCollections.observableArrayList();
            try {
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        Goal g = new Goal();
                        g.setPrimaryKey(rs.getString(1));
                        g.setGoal(rs.getString(2));
                        goalData.add(g);
                    }
                }
                else
                    System.out.println("Goal Result Set is empty!");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return goalData;
        };
        GoalPageController.setGoalDataSupplier(goalDataSupplier);

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
                    h.setHabitType(rs.getBoolean(3));
                    h.setHabitQuestion(rs.getString(4));
                    h.setUnit(rs.getString(5));
                    h.setTarget(rs.getDouble(6));
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
                    System.out.println("Habit Result Set is empty!");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return habitData;
        };
        HabitPageController.setHabitDataSupplier(habitDataSupplier);
    }

}
