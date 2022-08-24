package twm.habit_tracker.view.editPages;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
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
        ArrayList<String> years = new ArrayList<>();
        for (int i = START_YEAR; i <= endYear; i++)
            years.add(String.valueOf(i));
        yearBox.setItems(FXCollections.observableList(years));
        // Month
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
