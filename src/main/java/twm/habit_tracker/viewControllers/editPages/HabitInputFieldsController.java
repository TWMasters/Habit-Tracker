package twm.habit_tracker.viewControllers.editPages;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class HabitInputFieldsController extends InputAbstractController {
    private static Runnable targetHabitTable;

    @FXML private TextField nameInput;
    @FXML private TextField questionInput;

    @FXML private ToggleGroup Habit_Type;
    @FXML private RadioButton analogType;
    @FXML private RadioButton binaryType;

    @FXML private Label unitLabel;
    @FXML private TextField unitInput;
    @FXML private Label targetLabel;
    @FXML private TextField targetInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        super.generateListener(nameInput, 165);
        super.generateListener(questionInput, 165);
        super.generateListener(unitInput, 165);
        targetInput.textProperty().addListener(this::targetListener);
    }

    @Override
    public String[] getFields() {
        String[] output = {
                nameInput.getText(),
                getHabitType(),
                questionInput.getText(),
                getNullableValue(unitInput.getText()),
                getNullableValue(targetInput.getText())
        };
        return output;
    }

    private String getHabitType() {
        if (Habit_Type.getSelectedToggle().equals(analogType))
            return "false";
        else
            return "true";
    }

    public void isHabitTypeSelected() {
        if (Habit_Type.getSelectedToggle().equals(analogType))
            toggleVisibility(true);
        else
            toggleVisibility(false);
    }

    @Override
    public void setFields() {
        String[] inputData = this.getInputData().getAllFields();
        nameInput.setText(inputData[1]);
        setHabitType(Boolean.parseBoolean(inputData[2]));
        questionInput.setText(inputData[3]);
        unitInput.setText(inputData[4]);
        targetInput.setText(inputData[5]);
        isHabitTypeSelected();
    }

    private void setHabitType(Boolean flag) {
        if (flag) {
            binaryType.setSelected(true);
        }
        else {
            analogType.setSelected(true);
        }
    }

    @Override
    public void setTargetTable() {
        targetHabitTable.run();
    }

    public static void setTargetHabitTable(Runnable r) {
        targetHabitTable = r;
    }

    public void targetListener(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("(\\d)*\\.?\\d?\\d?")) {
            String amendedValue= newValue.substring(0, newValue.length() - 1);
            targetInput.setText(amendedValue);
        }
    }

    private void toggleVisibility(Boolean flag) {
        unitLabel.setVisible(flag);
        unitInput.setVisible(flag);
        targetLabel.setVisible(flag);
        targetInput.setVisible(flag);
    }
}
