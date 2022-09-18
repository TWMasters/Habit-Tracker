package twm.habit_tracker.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twm.habit_tracker.model.GoalTableState;
import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.HabitTrackerTableState;
import twm.habit_tracker.model.Model;
import twm.habit_tracker.viewControllers.data.Goal;
import twm.habit_tracker.viewControllers.data.Habit;
import twm.habit_tracker.viewControllers.data.HabitTracker;
import twm.habit_tracker.viewControllers.editPages.EditPage;
import twm.habit_tracker.viewControllers.editPages.GoalInputFieldsController;
import twm.habit_tracker.viewControllers.editPages.HabitInputFieldsController;
import twm.habit_tracker.viewControllers.mainPages.*;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.*;

public class ConcreteMasterController implements MasterController {
    private final int REWARD_DESCRIPTION_COLUMN_NO = 2;
    private final int REWARD_BODY_COLUMN_NO = 3;

    private final Model model;

    public ConcreteMasterController(Model model) throws SQLException {
        this.model = model;

        // Link View to Model
        setMenuPageMethods();

        setAvatarPageMethods();
        setGoalPageMethods();
        setHabitPageMethods();
        setTrophyPageMethods();

        setEditPageMethods();

    }

    @Override
    public void setAvatarPageMethods() throws SQLException {
        Runnable rewardMapRunnable = () -> {
            try {
                HashMap<String, ObservableList<String>> rewardMap = AvatarPageController.getRewardMap();
                ResultSet rs = model.getRewardManager().getRewards();
                if (rs.isBeforeFirst()) {
                    while (rs.next()) {
                        rewardMap.get(rs.getString(REWARD_BODY_COLUMN_NO)).add(rs.getString(REWARD_DESCRIPTION_COLUMN_NO));
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        };
        AvatarPageController.setRewardMapRunnable(rewardMapRunnable);

        Supplier<HashMap<String,String>> avatarStateSupplier = model.getRewardManager()::getAvatarState;
        AvatarPageController.setAvatarStateMapSupplier(avatarStateSupplier);

        BiConsumer<String,String> changeStateConsumer = (s1,s2) -> {
            model.getRewardManager().changeAvatarState(s1,s2);
        };
        AvatarPageController.setChangeStateConsumer(changeStateConsumer);

        BiFunction<Integer, Integer, ArrayList<String>> purchaseFunction = (i, j) -> {
            ArrayList<String>  outputMessages = model.getRewardManager().earnReward(i, j);
            MenuPageController.setCoins();
            return outputMessages;
        };
        AvatarPageController.setPurchaseTicketFunction(purchaseFunction);

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
        Function<String[],String> addEntryFunction = (s) -> {
            String primaryKey = model.addEntry(s);
            if (primaryKey.charAt(0) == '!')
                return primaryKey;
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
            return primaryKey;
        };
        EditPage.setAddEntryFunction(addEntryFunction);

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
                        String entryToBeDeleted = ";" + s + "=";
                        int index = oldCompleted.indexOf(entryToBeDeleted);
                        int offSet =  entryToBeDeleted.length() + 1;
                        String  newCompleted = oldCompleted.substring(0, index) + oldCompleted.substring(index + offSet);
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
        BiFunction<String[], String, String> editEntryFunction = (k, s) -> {
            String message = model.editEntry(k,s);
            return message;
        };
        EditPage.setEditEntryFunction(editEntryFunction);
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
                        g.setGoalDescription(rs.getString(3));
                        g.setDate(rs.getDate(4));
                        g.setAchieved(rs.getBoolean(5));
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

        // Mark goal as complete
        Consumer<String> markGoalAsComplete = (s) -> {
            model.changeTargetTable(new GoalTableState());
            ResultSet rs = model.getEntry(s);
            try {
                rs.next();
                String goalName = rs.getString(2);
                String goalDesc = rs.getString(3);
                String goalDeadline = rs.getString(4);
                String[] inputValues = { goalName, goalDesc, goalDeadline, "TRUE" };
                model.editEntry(inputValues, s);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        };
        GoalPageController.setMarkGoalAsCompleteConsumer(markGoalAsComplete);

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
                        output.add("CONGRATULATIONS!\n" + message);
                    }
                    MenuPageController.setCoins();
                    MenuPageController.setLevelInfo();
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
        Supplier<HashMap<String,Integer>> levelSupplier = () -> model.getRewardManager().getLevel();
        MenuPageController.setLevelInfoSupplier(levelSupplier);

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
