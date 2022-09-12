package twm.habit_tracker.controller.mainPages;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;

import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class TrophyPageController implements Initializable {

    private static ObservableMap<String, Boolean> trophyDataSet = FXCollections.observableHashMap();
    private static Runnable trophyDataRunnable;

    @FXML GridPane trophyGrid;
    @FXML Label dayLabel;
    @FXML Label monthLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTrophyData();
        MapChangeListener trophyListener = e -> setTrophies();
        trophyDataSet.addListener(trophyListener);
        setDay();
        setTrophies();

    }

    public static ObservableMap<String, Boolean> getTrophyDataSet() {
        return trophyDataSet;
    }

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

    /**
     * Help method which colours Trophies green if they have been earned!
     */
    private void setTrophies()  {
        for (Map.Entry<String,Boolean> entry : trophyDataSet.entrySet())
            if (entry.getValue()) {
                Optional<Node> targetLabel = trophyGrid.getChildren().stream()
                        .filter(n -> n.getId().equals(entry.getKey()))
                        .findFirst();
                targetLabel.get().setStyle("-fx-background-color: green;");
            }
    }

    public static void setTrophyData() { trophyDataRunnable.run(); }

    public static void setTrophyDataRunnable(Runnable r) {
        trophyDataRunnable = r;
    }

}
