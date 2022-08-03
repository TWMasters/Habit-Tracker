package twm.habit_tracker.view.mainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import twm.habit_tracker.view.Habit;
import twm.habit_tracker.view.ModelData;
import twm.habit_tracker.view.editPages.EditPage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class HabitPageController implements Initializable {
    private EditPage editPage;

    private static ObservableList<Habit> habitDataSet = FXCollections.observableArrayList();
    private static Supplier<ObservableList<Habit>> habitDataSupplier;

    @FXML
    private TableView<Habit> Habits_Table;

    /**
     * Method for when add button is pressed
     */
    public void addButtonPush() {
        editPage = new EditPage("AddButtons.fxml", "HabitInputFields.fxml");
        editPage.display("HABIT");
        getHabitData();
        buildTable();
    }

    /**
     * Helper method to build table
     */
    private void buildTable() {
        // https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx
        Habits_Table.setItems(habitDataSet);
        Habits_Table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("habit"));
        Habits_Table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("habitQuestion"));
    }

    public void editTablePush() {
        ModelData item = Habits_Table.getSelectionModel().getSelectedItem();
        if (item != null) System.out.println(item.getPrimaryKey());
    }

    public void getHabitData() {
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
