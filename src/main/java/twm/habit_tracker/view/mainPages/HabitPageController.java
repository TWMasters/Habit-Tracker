package twm.habit_tracker.view.mainPages;

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
import twm.habit_tracker.view.Habit;
import twm.habit_tracker.view.HabitTracker;
import twm.habit_tracker.view.editPages.EditPage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class HabitPageController implements Initializable {

    private static final String EMPTY_TRACKER = "empty";

    private EditPage editPage;
    private EventHandler<ActionEvent> editButtonPush;

    private static BiConsumer<LocalDate, String> updateHabitTrackerCompletedAttributeBiConsumer;
    private static Function<String, Habit> getHabitEntryFunction;
    private static Function<LocalDate, HabitTracker> getHabitTrackerEntryFunction;
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

        buildHabitTracker();
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
            GridPane.setConstraints(checkBox, 3, row_count, 1,  1);

            Habits_Container.getChildren().addAll(button, label, checkBox);
            row_count ++;
        }

    }

    /**
     * Helper method to build habit tracker
     */
    private void buildHabitTracker() {
        LocalDate today = LocalDate.now();
        HabitTracker ht = getHabitTrackerEntryFunction.apply(today);
        if (ht.getCompleted() == null) {
            String output = "";
            for (Habit h : habitDataSet) {
                String entry = String.format("%s=0;");
                output += entry;
            }
            updateHabitTrackerCompletedAttributeBiConsumer.accept(today, output);
        };
    }

    /**
     * Helper method to convert String into Map
     * @param input Completed Attribute from Habit_Tracker
     * @return Mapping of whether habits have been completed for the day
     */
    private Map<String,String> convertToMap(String input) {
        if (input.equals(""))
                return null;
        Map<String,String> result = Arrays.stream(input.split(";"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(
                        a -> a[0],
                        a -> a[1]
                ));
        return result;
    }

    public void getHabitData() {
        habitDataSet = habitDataSupplier.get();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getHabitData();
        buildHabitsContainer();

    }

    public static void setGetHabitEntryFunction(Function<String, Habit> function) {
        HabitPageController.getHabitEntryFunction = function;
    }

    public static void setGetHabitTrackerEntryFunction(Function<LocalDate, HabitTracker> function) {
        HabitPageController.getHabitTrackerEntryFunction = function;
    }

    public static void setHabitDataSupplier(Supplier<ObservableList<Habit>> supplier) {
        habitDataSupplier = supplier;
    }

    public static void setUpdateHabitTrackerCompletedAttributeBiConsumer(BiConsumer<LocalDate, String> biConsumer) {
        updateHabitTrackerCompletedAttributeBiConsumer = biConsumer;
    }


}
