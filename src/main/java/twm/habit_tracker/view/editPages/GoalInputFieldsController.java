package twm.habit_tracker.view.editPages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GoalInputFieldsController extends InputAbstractController{

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

    }
}
