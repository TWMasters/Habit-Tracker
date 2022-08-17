package twm.habit_tracker.view.editPages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GoalInputFieldsController extends InputAbstractController{
    private static Runnable targetGoalTable;

    @FXML TextField goalInput;
    @FXML TextField descriptionInput;
    @FXML TextField deadlineInput;

    @Override
    public String[] getFields() {
        String[] output = {
                goalInput.getText(),
                getNullableValue(descriptionInput),
                getNullableValue(deadlineInput)
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
