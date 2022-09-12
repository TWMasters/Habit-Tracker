package twm.habit_tracker.controller.editPages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;


import java.net.URL;
import java.util.ResourceBundle;

public class EditPageContainerController implements Initializable {

    @FXML
    BorderPane editPageBorderFrame;

    public void setButtons(Node buttons) {
        editPageBorderFrame.setTop(buttons);
    }

    public void setInputFields(Node inputFields) {
        editPageBorderFrame.setCenter(inputFields);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editPageBorderFrame.setStyle("-fx-border-color: black");

    }
}
