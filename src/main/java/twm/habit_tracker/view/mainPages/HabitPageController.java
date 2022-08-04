package twm.habit_tracker.view.mainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import twm.habit_tracker.view.Habit;
import twm.habit_tracker.view.editPages.EditPage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Supplier;

public class HabitPageController implements Initializable {

    private EditPage editPage;
    private EventHandler<ActionEvent> editButtonPush;
    private static Function<String, Habit> getHabitEntryFunction;
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
        // https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx
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

            RadioButton radio = new RadioButton();
            radio.setId("radio" + h.getPrimaryKey());
            GridPane.setConstraints(radio, 3, row_count, 1,  1);

            Habits_Container.getChildren().addAll(button, label, radio);
            row_count ++;

        }

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

    public static void setHabitDataSupplier(Supplier<ObservableList<Habit>> supplier) {
        habitDataSupplier = supplier;
    }


}
