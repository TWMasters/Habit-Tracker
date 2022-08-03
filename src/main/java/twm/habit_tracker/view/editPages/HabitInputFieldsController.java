package twm.habit_tracker.view.editPages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HabitInputFieldsController extends InputAbstractController {
    @FXML
    private TextField nameInput;
    @FXML
    private TextField questionInput;

    @Override
    public String[] getFields() {
        String[] output = { nameInput.getText(), questionInput.getText() };
        return output;
    }
}
