package twm.habit_tracker.view.habitPage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import twm.habit_tracker.view.Habit;
import twm.habit_tracker.view.PopupMenu;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class HabitPage implements Initializable {
    private static final String HABIT_EDIT_URL = "src/main/java/twm/habit_tracker/view/habitEditPage/HabitEditPage.fxml";

    private static ObservableList<Habit> habitDataSet = FXCollections.observableArrayList();
    private static Supplier<ObservableList<Habit>> habitDataSupplier;

    @FXML
    private TableView<Habit> Habits_Table;

    public void addButtonPush() {
        PopupMenu.display("HABIT", HABIT_EDIT_URL);
    }

    /**
     * Helper method to build table
     */
    private void buildTable() {
        Habits_Table.setItems(habitDataSet);
        Habits_Table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("habit"));
        Habits_Table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("habitQuestion"));
    }

    public static void getHabitData() {
        habitDataSet = habitDataSupplier.get();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getHabitData();
        buildTable();

    }

    public static void setHabitDataSupplier(Supplier<ObservableList<Habit>> f) {
        habitDataSupplier = f;
    }

}
