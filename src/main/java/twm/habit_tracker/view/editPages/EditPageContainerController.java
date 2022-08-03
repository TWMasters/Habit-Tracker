package twm.habit_tracker.view.editPages;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class EditPageContainerController {
    @FXML
    BorderPane editPageBorderFrame;

    public void setButtons(Node buttons) {
        editPageBorderFrame.setTop(buttons);
    }
}
