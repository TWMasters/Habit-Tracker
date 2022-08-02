package twm.habit_tracker.view.habitEditPage;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twm.habit_tracker.view.habitPage.HabitPage;

import java.util.function.Consumer;

public class HabitEditPage {
    private static Consumer<String[]> addHabitConsumer;

    @FXML
    private BorderPane habitEditFrame;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField questionInput;

    private void exit() {
        Stage currentWindow = (Stage) habitEditFrame.getScene().getWindow();
        currentWindow.close();
    }

    public static void setAddHabitConsumer(Consumer<String[]> addHabitConsumer) {
        HabitEditPage.addHabitConsumer = addHabitConsumer;
    }

    public void saveButtonPush() {
        String[] input = {nameInput.getText(), questionInput.getText()};
        addHabitConsumer.accept(input);
        HabitPage.getHabitData();
        exit();
    }
}
