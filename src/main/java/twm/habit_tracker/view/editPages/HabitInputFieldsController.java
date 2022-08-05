package twm.habit_tracker.view.editPages;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class HabitInputFieldsController extends InputAbstractController {
    @FXML private TextField nameInput;
    @FXML private TextField questionInput;

    @FXML private ToggleGroup Habit_Type;
    @FXML private RadioButton analogType;

    @FXML private Label unitLabel;
    @FXML private TextField unitInput;
    @FXML private Label targetLabel;
    @FXML private TextField targetInput;


    @Override
    public String[] getFields() {
        String[] output = {
                nameInput.getText(),
                getHabitType(),
                questionInput.getText(),
                getNullableValue(unitInput),
                getNullableValue(targetInput)
        };
        return output;
    }

    private String getHabitType() {
        if (Habit_Type.getSelectedToggle().equals(analogType))
            return "false";
        else
            return "true";
    }

    private String getNullableValue(TextField inputField) {
        String output = inputField.getText();
        if (output.equals(""))
            return "null";
        else
            return output;
    }

    public void habitTypeSelected() {
        if (Habit_Type.getSelectedToggle().equals(analogType))
            toggleVisibility(true);
        else
            toggleVisibility(false);
    }

    @Override
    public void setFields() {
        String[] inputData = this.getInputData().getAllFields();
        nameInput.setText(inputData[1]);
        questionInput.setText(inputData[2]);
    }

    private void toggleVisibility(Boolean flag) {
        unitLabel.setVisible(flag);
        unitInput.setVisible(flag);
        targetLabel.setVisible(flag);
        targetInput.setVisible(flag);
    }
}
