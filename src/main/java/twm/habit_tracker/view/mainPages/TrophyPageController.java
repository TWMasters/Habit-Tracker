package twm.habit_tracker.view.mainPages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class TrophyPageController implements Initializable {
    @FXML Label dayLabel;
    @FXML Label monthLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String day = String.valueOf(LocalDate.now().getDayOfMonth());
        String month = LocalDate.now().getMonth().name();
        String year = String.valueOf(LocalDate.now().getYear());
        switch (LocalDate.now().getDayOfMonth() % 10) {
            case 1:
                day += "st";
                break;
            case 2:
                day += "nd";
                break;
            case 3:
                day += "rd";
                break;
            default:
                day += "th";

        }
        dayLabel.setText(String.format("%s %s %s", day, month, year));
    }

    private String makeOrdinal(String input) {
        int index = input.length() - 1;
        input = input.substring(index);
        return null;
    }
}
