package twm.habit_tracker.view.mainPages;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twm.habit_tracker.view.View;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

// TODO: 26/08/2022 Reusable object for buttons! 
public class MenuPageController implements Initializable {
    private static IntegerProperty coins = new SimpleIntegerProperty();
    private static IntegerProperty coinBalance = new SimpleIntegerProperty();
    private static Supplier<Integer> coinSupplier;
    private static View context;

    @FXML Label coinLabel;

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
        TrophyPageController.setTrophyData();
        Node page = context.getPage("Trophy");
        mainFrame.setCenter(page);
        BorderPane.setAlignment(page, Pos.TOP_LEFT);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InvalidationListener coinListener = e -> {
            coinLabel.setText(String.valueOf(coins.get()));
        };
        coins.addListener(coinListener);
        setCoins();
        habitButtonPush();

    }
    public static void setContext(View v) {
        context = v;
    }

    public static void setCoins() {
        coins.setValue(coinSupplier.get());
    }

    public static void setCoinSupplier(Supplier<Integer> supplier) {
        coinSupplier = supplier;
    }
}
