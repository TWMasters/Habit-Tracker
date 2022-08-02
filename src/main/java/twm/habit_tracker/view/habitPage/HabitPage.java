package twm.habit_tracker.view.habitPage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import twm.habit_tracker.view.Habit;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class HabitPage implements Initializable {
    private static ObservableList<Habit> habitDataSet = FXCollections.observableArrayList();
    private static Supplier<ObservableList<Habit>> getHabitData;

    @FXML
    private TableView<Habit> Habits_Table;

    private void buildTable() {
        Habits_Table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("habit"));
        Habits_Table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("habitQuestion"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        habitDataSet = getHabitData.get();
        buildTable();

    }

    public static void setGetHabitData(Supplier<ObservableList<Habit>> f) {
        getHabitData = f;
    }


}
