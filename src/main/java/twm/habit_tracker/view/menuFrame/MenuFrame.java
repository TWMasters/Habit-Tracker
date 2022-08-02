package twm.habit_tracker.view.menuFrame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twm.habit_tracker.view.GUIView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuFrame implements Initializable {
    private static final String GOAL_PAGE_URL = "src/main/java/twm/habit_tracker/view/goalPage/GoalPage.fxml";
    private static final String HABIT_PAGE_URL = "src/main/java/twm/habit_tracker/view/habitPage/HabitPage.fxml";

    @FXML
    private BorderPane mainFrame;

    public void exitButtonPush() {
        Stage currentWindow = (Stage) mainFrame.getScene().getWindow();
        currentWindow.close();
    }

    public void goalButtonPush() {
        Node page = GUIView.getFXMLResource(GOAL_PAGE_URL);
        mainFrame.setCenter(page);
        BorderPane.setAlignment(page, Pos.TOP_LEFT);
    }

    public void habitButtonPush() {
        Node page = GUIView.getFXMLResource(HABIT_PAGE_URL);
        mainFrame.setCenter(page);
        BorderPane.setAlignment(page, Pos.TOP_LEFT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        habitButtonPush();

    }
}
