package twm.habit_tracker.view.mainPages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twm.habit_tracker.view.View;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuPageController implements Initializable {
    private static View context;

    @FXML
    private BorderPane mainFrame;

    public void exitButtonPush() {
        Stage currentWindow = (Stage) mainFrame.getScene().getWindow();
        currentWindow.close();
    }

    public void goalButtonPush() {
        Node page = context.getPage("Goal");
        mainFrame.setCenter(page);
        BorderPane.setAlignment(page, Pos.TOP_LEFT);

    }

    public void habitButtonPush() {
        Node page = context.getPage("Habit");
        mainFrame.setCenter(page);
        BorderPane.setAlignment(page, Pos.TOP_LEFT);

    }

    public void trophyButtonPush() {
        Node page = context.getPage("Trophy");
        mainFrame.setCenter(page);
        BorderPane.setAlignment(page, Pos.TOP_LEFT);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        habitButtonPush();

    }

    public static void setContext(View v) {
        context = v;
    }
}
