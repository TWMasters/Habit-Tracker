package twm.habit_tracker.viewControllers.mainPages;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import twm.habit_tracker.viewControllers.data.Habit;
import twm.habit_tracker.viewControllers.data.HabitTracker;
import twm.habit_tracker.viewControllers.editPages.EditPage;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Controller for Habit Page
 */
public class HabitPageController implements Initializable {

    private EditPage editPage;
    private EventHandler<ActionEvent> editButtonPush;

    private static BiFunction<LocalDate, String[], String[]> updateHabitTrackerCompletedAttributeBiConsumer;
    private static Function<String, Habit> getHabitEntryFunction;
    private static Function<LocalDate, HabitTracker> getHabitTrackerEntryFunction;
    private static int habitCount;
    private static ObservableList<Habit> habitDataSet;
    private static Supplier<ObservableList<Habit>> habitDataSupplier;

    @FXML
    private GridPane Habits_Container;

    {
        habitDataSet = FXCollections.observableArrayList();
        editButtonPush = e -> {
            Button buttonClicked = (Button) e.getTarget();
            String id = buttonClicked.getId().substring(6);
            Habit h = getHabitEntryFunction.apply(id);
            editPage = new EditPage("EditButtons.fxml", "HabitInputFields.fxml", h);
            editPage.display("HABIT");
            getHabitData();
            buildHabitsContainer();
        };
    }

    /**
     * Method for when add button is pressed
     */
    public void addButtonPush() {
        editPage = new EditPage("AddButtons.fxml", "HabitInputFields.fxml");
        editPage.display("HABIT");
        getHabitData();
        buildHabitsContainer();
    }

    /**
     * Helper method to build table
     */
    private void buildHabitsContainer() {
        habitCount = habitDataSet.size();

        // Habit Tracker
        buildHabitTracker(getHabitTrackerEntryFunction.apply(LocalDate.now()));
        HabitTracker ht = getHabitTrackerEntryFunction.apply(LocalDate.now());
        Map<String,String> habitsCompletedMap = convertToMap(ht.getCompleted());

        // Build Container
        Habits_Container.getChildren().clear();
        int row_count = 0;
        for (Habit h: habitDataSet ) {
            Button button  = new Button(h.getHabit());
            button.setAlignment(Pos.CENTER);
            button.setPrefSize(144, 60);
            button.setStyle("-fx-font-size: 18");
            button.setId("button" + h.getPrimaryKey());
            button.setOnAction(editButtonPush);
            GridPane.setConstraints(button, 0, row_count, 1, 1);

            Label label = new Label(h.getHabitQuestion());
            label.setStyle("-fx-font-size: 18");
            GridPane.setConstraints(label, 1, row_count, 2, 1);

            CheckBox checkBox = new CheckBox();
            checkBox.setPrefSize(72,  72);
            checkBox.setId("checkBox" + h.getPrimaryKey());

            // Check if habit ticked
            if (habitsCompletedMap.get(h.getPrimaryKey()) != null && habitsCompletedMap.get(h.getPrimaryKey()).equals("1")) {
                checkBox.setSelected(true);
                checkBox.setDisable(true);
            }
            // Function to check box
            checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                String habitID = checkBox.getId().substring(8);
                habitsCompletedMap.put(habitID, "1");
                String[] input = {String.valueOf(habitCount), convertToString(habitsCompletedMap)};
                String[] messages = updateHabitTrackerCompletedAttributeBiConsumer.apply(LocalDate.now(), input);
                for (String s : messages)
                    Message.display(s);
                checkBox.setDisable(true);
            });
            GridPane.setConstraints(checkBox, 3, row_count, 1,  1);
            Habits_Container.getChildren().addAll(button, label, checkBox);

            row_count ++;
        }

    }

    /**
     * Create empty completed field for Habit Tracking
     * @param ht Today's Habit Tracking Information
     */
    private void buildHabitTracker(HabitTracker ht) {
        if (ht.getCompleted().equals("")) {
            String input = "";
            for (Habit h : habitDataSet)
                input += ";" + h.getPrimaryKey() + "=0";
            System.out.println(input);
            String[] inputArray = {String.valueOf(habitDataSet.size()), input};
            updateHabitTrackerCompletedAttributeBiConsumer.apply(LocalDate.now(), inputArray);
        }
    }

    /**
     * Helper method to convert String into Map
     * @param input Completed Attribute from Habit_Tracker
     * @return Mapping of whether habits have been completed for the day
     */
    private Map<String,String> convertToMap(String input) {
        Map<String,String> result = new HashMap<>();
        if (input.equals("")) {
            return result;
        }
        result = Arrays.stream(input.split(";"))
                .skip(1)
                .map(s -> s.split("="))
                .collect(Collectors.toMap(
                        a -> a[0],
                        a -> a[1]
                ));
        return result;
    }

    /**
     * Helper method to convert Map into String for updating Habit Tracker Relation
     * @param map of Goals and completed state
     * @return String form of above map
     */
    private String convertToString(Map<String,String> map) {
        String output = map.entrySet().stream()
                .map(e -> new String(";" + e.getKey() + "=" + e.getValue()))
                .reduce("", (substring, string) -> string + substring);
        return output;
    }

    /**
     * Run Habit Supplier
     */
    public void getHabitData() {
        habitDataSet = habitDataSupplier.get();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getHabitData();
        buildHabitsContainer();

    }

    /**
     * Set code to get a single Habit
     * @param function
     */
    public static void setGetHabitEntryFunction(Function<String, Habit> function) {
        HabitPageController.getHabitEntryFunction = function;
    }

    /**
     * Set code to get Habit Tracking data for a given date
     * @param function
     */
    public static void setGetHabitTrackerEntryFunction(Function<LocalDate, HabitTracker> function) {
        HabitPageController.getHabitTrackerEntryFunction = function;
    }

    /**
     * Set code to get Habit table
     * @param supplier
     */
    public static void setHabitDataSupplier(Supplier<ObservableList<Habit>> supplier) {
        habitDataSupplier = supplier;
    }

    /**
     * Set code to update Habit Tracking data for a given date
     * @param biFunction
     */
    public static void setUpdateHabitTrackerCompletedAttributeBiConsumer(BiFunction<LocalDate, String[], String[]> biFunction) {
        updateHabitTrackerCompletedAttributeBiConsumer = biFunction;
    }


}
