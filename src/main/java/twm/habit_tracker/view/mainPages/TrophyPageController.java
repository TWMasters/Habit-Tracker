package twm.habit_tracker.view.mainPages;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import twm.habit_tracker.view.data.Trophy;

import java.net.URL;
import java.time.LocalDate;

import java.util.ResourceBundle;
import java.util.function.Supplier;

public class TrophyPageController implements Initializable {

    private static ObservableList<Trophy> trophyDataSet;
    private static Supplier<ObservableList<Trophy>> trophyDataSupplier;

    @FXML Label dayLabel;
    @FXML Label monthLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDay();


    }

    public void getTrophyData() { trophyDataSet = trophyDataSupplier.get(); }

    /**
     * Helper Method to set date which appears on the Trophy Page
     */
    private void setDay() {
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

    public static void setTrophyDataSupplier(Supplier<ObservableList<Trophy>> supplier) {
        trophyDataSupplier = supplier;
    }

}
