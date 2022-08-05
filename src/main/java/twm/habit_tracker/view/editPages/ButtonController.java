package twm.habit_tracker.view.editPages;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;

public class ButtonController {
    private EditPage context;

    @FXML
    ButtonBar buttonBarReference;

    public void addButtonPush() {
        context.add();
    }

    public void backButtonPush() {
        context.back();
    }


    public void deleteButtonPush() {
        context.delete();
    }

    public void saveButtonPush() {
        context.save();
    }

    public void setContext(EditPage context) {
        this.context = context;
    }

}
