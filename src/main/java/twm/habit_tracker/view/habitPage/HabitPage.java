package twm.habit_tracker.view.habitPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class HabitPage implements Initializable {
    private static ResultSet HabitData;
    private static Supplier<ResultSet> getHabitData;

    @FXML
    private TableView Habits_Table;

    public static void setGetHabitData(Supplier<ResultSet> f) {
        getHabitData = f;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
