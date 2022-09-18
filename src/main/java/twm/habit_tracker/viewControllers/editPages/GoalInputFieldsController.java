package twm.habit_tracker.viewControllers.editPages;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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
    @FXML TextArea descriptionInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        super.generateListener(goalInput, 165);
        super.generateListener(descriptionInput, 1000);

        // Year
        int endYear = LocalDate.now().getYear() + FIFTY_YEARS;
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = START_YEAR; i <= endYear; i++)
            years.add(i);
        yearBox.setItems(FXCollections.observableList(years));

        // Month
        ArrayList<Integer> months = new ArrayList<>();
        for (int i = Month.JANUARY.getValue(); i <= Month.DECEMBER.getValue(); i++)
            months.add(i);
        monthBox.setItems(FXCollections.observableList(months));

        // Day
        monthBox.setOnAction(e -> refreshDates());
        yearBox.setOnAction(e -> refreshDates());

        //Set default Dates
        if (getInputData() == null) {
            LocalDate date = LocalDate.now();
            monthBox.setValue(date.getMonthValue());
            yearBox.setValue(date.getYear());
            dayBox.setValue(date.getDayOfMonth());
            refreshDates();
        }
    }


    @Override
    public String[] getFields() {
        String date = String.format("%s-%s-%s"
                , yearBox.getValue().toString()
                , monthBox.getValue().toString()
                , dayBox.getValue().toString());
        System.out.println(date);
        String[] output = {
                goalInput.getText(),
                getNullableValue(descriptionInput.getText()),
                date,
                "false"
        };
        return output;
    }

    private void refreshDates() {
        Boolean flag =  (monthBox.getValue() != null && yearBox.getValue() != null);
        if (flag) {
            ArrayList<Integer> days = new ArrayList<>();
            int endDay = YearMonth.of((int)yearBox.getValue(), (int)monthBox.getValue()).lengthOfMonth();
            for (int i = 1; i <= endDay; i++ )
                days.add(i);
            dayBox.setItems(FXCollections.observableList(days));
        }
    }

    @Override
    public void setFields() {
        String[] inputData = this.getInputData().getAllFields();
        goalInput.setText(inputData[1]);
        descriptionInput.setText(inputData[2]);
        // Populate dates
        if (!inputData[3].equals("")) {
            LocalDate date = LocalDate.parse(inputData[3]);
            monthBox.setValue(date.getMonthValue());
            yearBox.setValue(date.getYear());
            dayBox.setValue(date.getDayOfMonth());
            refreshDates();
        }
    }

    @Override
    public void setTargetTable() {
        targetGoalTable.run();
    }

    public static void setTargetGoalTable(Runnable r) {
        targetGoalTable = r;
    }
}
