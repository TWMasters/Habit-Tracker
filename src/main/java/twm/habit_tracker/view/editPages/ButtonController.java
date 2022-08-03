package twm.habit_tracker.view.editPages;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;

public class ButtonController {
    private EditPage context;

    @FXML
    ButtonBar buttonBarReference;

    public void addButtonPush() {
        context.add();
    }

    public void backButtonPush() {
        Stage currentWindow = (Stage) buttonBarReference.getScene().getWindow();
        currentWindow.close();
    }


    public void deleteButtonPush() {
        context.delete();
    }

    public void setContext(EditPage context) {
        this.context = context;
    }

}
