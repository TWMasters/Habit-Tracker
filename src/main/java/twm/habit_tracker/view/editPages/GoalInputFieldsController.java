package twm.habit_tracker.view.editPages;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GoalInputFieldsController extends InputAbstractController{

    private static final int FIFTY_YEARS = 50;
    private static final int START_YEAR = 2022;

    private static Runnable targetGoalTable;

    @FXML ComboBox dayBox;
    @FXML ComboBox monthBox;
    @FXML ComboBox yearBox;
    @FXML TextField goalInput;
    @FXML TextField descriptionInput;
    @FXML TextField deadlineInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        // Year
        int endYear = LocalDate.now().getYear() + FIFTY_YEARS;
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = START_YEAR; i <= endYear; i++)
            years.add(i);
        yearBox.setItems(FXCollections.observableList(years));
        yearBox.setValue(LocalDate.now().getYear());

        // Month
        ArrayList<Integer> months = new ArrayList<>();
        for (int i = Month.JANUARY.getValue(); i <= Month.DECEMBER.getValue(); i++)
            months.add(i);
        monthBox.setItems(FXCollections.observableList(months));
        monthBox.setValue(LocalDate.now().getMonthValue());

        // Day
        monthBox.setOnAction(e -> refreshDates());
        yearBox.setOnAction(e -> refreshDates());
        refreshDates();
        dayBox.setValue(LocalDate.now().getDayOfMonth());

    }


    @Override
    public String[] getFields() {
        String[] output = {
                goalInput.getText(),
                getNullableValue(descriptionInput),
                getNullableValue(deadlineInput),
                "false"
        };
        return output;
    }

    private void refreshDates() {
        ArrayList<Integer> days = new ArrayList<>();
        int endDay = YearMonth.of((int)yearBox.getValue(), (int)monthBox.getValue()).lengthOfMonth();
        for (int i = 1; i <= endDay; i++ )
            days.add(i);
        dayBox.setItems(FXCollections.observableList(days));
    }

    @Override
    public void setFields() {
        String[] inputData = this.getInputData().getAllFields();
        goalInput.setText(inputData[1]);
        descriptionInput.setText(inputData[2]);
        deadlineInput.setText(inputData[3]);
    }

    @Override
    public void setTargetTable() {
        targetGoalTable.run();
    }

    public static void setTargetGoalTable(Runnable r) {
        targetGoalTable = r;
    }
}
