package twm.habit_tracker.view.mainPages;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import twm.habit_tracker.view.data.Trophy;

import java.net.URL;
import java.time.LocalDate;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class TrophyPageController implements Initializable {

    private static ObservableList<Trophy> trophyDataSet = FXCollections.observableArrayList();
    private static Supplier<String> trophyDataSupplier;

    @FXML GridPane trophyGrid;
    @FXML Label dayLabel;
    @FXML Label monthLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTrophyData();
        ListChangeListener trophyListener = e -> setTrophies();
        trophyDataSet.addListener(trophyListener);
        setDay();
        setTrophies();

    }

    public static ObservableList<Trophy> getTrophyDataSet() {
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
        for (Trophy t : trophyDataSet)
            if (t.isTrophyWon()) {
                Optional<Node> targetLabel = trophyGrid.getChildren().stream()
                        .filter(n -> n.getId().equals(t.getPrimaryKey()))
                        .findFirst();
                targetLabel.get().setStyle("-fx-background-color: green;");
            }

    }

    public static void setTrophyData() { trophyDataSupplier.get(); }

    public static void setTrophyDataSupplier(Supplier<String> supplier) {
        trophyDataSupplier = supplier;
    }

}
