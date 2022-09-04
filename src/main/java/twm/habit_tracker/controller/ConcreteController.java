package twm.habit_tracker.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twm.habit_tracker.model.GoalTableState;
import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.HabitTrackerTableState;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.view.data.Goal;
import twm.habit_tracker.view.data.Habit;
import twm.habit_tracker.view.data.HabitTracker;
import twm.habit_tracker.view.View;
import twm.habit_tracker.view.data.Trophy;
import twm.habit_tracker.view.editPages.EditPage;
import twm.habit_tracker.view.editPages.GoalInputFieldsController;
import twm.habit_tracker.view.editPages.HabitInputFieldsController;
import twm.habit_tracker.view.mainPages.GoalPageController;
import twm.habit_tracker.view.mainPages.HabitPageController;
import twm.habit_tracker.view.mainPages.MenuPageController;
import twm.habit_tracker.view.mainPages.TrophyPageController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.*;

public class ConcreteController implements Controller {
    private final View view;
    private final Model model;

    public ConcreteController(View view, Model model) throws SQLException {
        this.view = view;
        this.model = model;

        // Link View to Model
        setMenuPageMethods();

        setGoalPageMethods();
        setHabitPageMethods();
        setTrophyPageMethods();

        setEditPageMethods();

    }

    @Override
    public void setEditPageMethods() throws SQLException {
        // Set Target Table
        Runnable goalRunnable = () -> {
            model.changeTargetTable(new GoalTableState());
            System.out.println(model.getTableState());
        };
        GoalInputFieldsController.setTargetGoalTable(goalRunnable);

        Runnable habitRunnable = () -> {
            model.changeTargetTable(new HabitTableState());
            System.out.println(model.getTableState());
        };
        HabitInputFieldsController.setTargetHabitTable(habitRunnable);

        // Add Habit or Goal
        Consumer<String[]> addEntryConsumer = (s) -> {
            String primaryKey = model.addEntry(s);
            if (model.getTableState().equals("Habit Table State")) {
                model.changeTargetTable(new HabitTrackerTableState());
                ResultSet rs = model.getEntry(LocalDate.now().toString());
                try {
                    if (rs.isBeforeFirst()) {
                        rs.next();
                        String target = String.valueOf(rs.getInt(2) + 1);
                        String oldCompleted = rs.getString(3);
                        String[] input = {target, oldCompleted + ";" + primaryKey + "=0"};
                        model.editEntry(input, LocalDate.now().toString());
                    }
                }
                catch (SQLException e) {
                    System.err.println("Error on Adding Habit");
                }
            }
        };
        EditPage.setAddEntryConsumer(addEntryConsumer);

        //Delete Habit or Goal
        Consumer<String> deleteEntryConsumer = (s) -> {
            model.deleteEntry(s);
            if (model.getTableState().equals("Habit Table State")) {
                model.changeTargetTable(new HabitTrackerTableState());
                ResultSet rs = model.getEntry(LocalDate.now().toString());
                try {
                    if (rs.isBeforeFirst()) {
                        rs.next();
                        String target = String.valueOf(rs.getInt(2) - 1);
                        String oldCompleted = rs.getString(3);
                        int index = oldCompleted.indexOf(";" + s + "=");
                        String  newCompleted = oldCompleted.substring(0, index) + oldCompleted.substring(index + 4);
                        String[] input = {target, newCompleted};
                        model.editEntry(input, LocalDate.now().toString());
                    }
                }
                catch (SQLException e) {
                    System.err.println("Error on Deleting Habit");
                }
            }
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
        // Retrieve single Goal
        Function<String, Goal> getGoalEntryFunction = (id) -> {
            model.changeTargetTable(new GoalTableState());
            ResultSet rs = model.getEntry(id);
            try {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    Goal g = new Goal();
                    g.setPrimaryKey(rs.getString(1));
                    g.setGoal(rs.getString(2));
                    g.setGoalDescription(rs.getString(3));
                    g.setDate(rs.getDate(4));
                    g.setAchieved(rs.getBoolean(5));
                    return g;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        };
        GoalPageController.setGetGoalEntryFunction(getGoalEntryFunction);

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
        // Retrieve single Habit Entry
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

        // Retrieve single HabitTracker Entry
        Function<LocalDate, HabitTracker> getHabitTrackerEntryFunction = (date) -> {
            model.changeTargetTable(new HabitTrackerTableState());
            ResultSet rs = model.getEntry(date.toString());
            try {
                if (rs.isBeforeFirst()) {
                    rs.next();
                    HabitTracker ht = new HabitTracker();
                    ht.setPrimaryKey(rs.getString(1));
                    ht.setTarget(rs.getInt(2));
                    ht.setCompleted(rs.getString(3));
                    return ht;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        };
        HabitPageController.setGetHabitTrackerEntryFunction(getHabitTrackerEntryFunction);

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

        //Update HabitTracker Completed
        BiFunction<LocalDate, String[], String[]> updateHabitTrackerCompleted = (date, values) -> {
            model.changeTargetTable(new HabitTrackerTableState());
            model.editEntry(values, date.toString());
            // Trophy
            Optional<ResultSet> trophyOutput = model.getRewardManager().checkTrophies();
            ArrayList<String> output = new ArrayList<>();
            try {
                if (trophyOutput.isPresent()) {
                    ResultSet workingRS = trophyOutput.get();
                    while (workingRS.next()) {
                        String key = workingRS.getString(1);
                        String message = workingRS.getString(4);
                        TrophyPageController.getTrophyDataSet().replace(key, true);
                        output.add(message);
                    }
                }
            }
            catch (SQLException e) {
                System.err.println("Error while  updating trophy table");
                e.printStackTrace();
            }
            return output.toArray(new String[output.size()]);
        };
        HabitPageController.setUpdateHabitTrackerCompletedAttributeBiConsumer(updateHabitTrackerCompleted);
    }

    @Override
    public void setMenuPageMethods() {
        Supplier<Integer> coinSupplier = () -> model.getRewardManager().getBalance();
        MenuPageController.setCoinSupplier(coinSupplier);

    }

    @Override
    public void setTrophyPageMethods()  {
        Runnable trophyDataRunnable = () -> {
            ResultSet rs = model.getRewardManager().getTrophies();
            try {
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        String key = rs.getString(1);
                        Boolean value = rs.getBoolean(2);
                        TrophyPageController.getTrophyDataSet().put(key, value);
                    }
                }
                else
                    System.out.println("Trophy Set is empty!");
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        };
        TrophyPageController.setTrophyDataRunnable(trophyDataRunnable);

    }
}
