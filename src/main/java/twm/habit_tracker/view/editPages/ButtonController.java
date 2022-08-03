package twm.habit_tracker.view.editPages;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;

public class ButtonController {

    @FXML
    ButtonBar buttonBarReference;

    public void addButtonPush() {

    }

    public void backButtonPush() {
        Stage currentWindow = (Stage) buttonBarReference.getScene().getWindow();
        currentWindow.close();
    }

}
