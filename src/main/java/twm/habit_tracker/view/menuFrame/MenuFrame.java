package twm.habit_tracker.view.menuFrame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuFrame implements Initializable {
    private static final String GOAL_PAGE_URL = "src/main/java/twm/habit_tracker/view/goalPage/GoalPage.fxml";
    private static URL goalPageURL;

    private static final String HABIT_PAGE_URL = "src/main/java/twm/habit_tracker/view/habitPage/HabitPage.fxml";
    private static URL habitPageURL;

    @FXML
    private BorderPane mainFrame;

    public void exitButtonPush() {
        Stage currentWindow = (Stage) mainFrame.getScene().getWindow();
        currentWindow.close();
    }

    public void goalButtonPush() {
        try {
            Node page = FXMLLoader.load(goalPageURL);
            mainFrame.setCenter(page);
            BorderPane.setAlignment(page, Pos.TOP_LEFT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void habitButtonPush() {
        try {
            Node page = FXMLLoader.load(habitPageURL);
            mainFrame.setCenter(page);
            BorderPane.setAlignment(page, Pos.TOP_LEFT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            habitPageURL = new File(HABIT_PAGE_URL).toURI().toURL();
            goalPageURL = new File(GOAL_PAGE_URL).toURI().toURL();
            habitButtonPush();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }

    }
}
