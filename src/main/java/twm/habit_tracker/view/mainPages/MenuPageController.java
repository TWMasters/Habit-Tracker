package twm.habit_tracker.view.mainPages;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twm.habit_tracker.view.View;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;

// TODO: 26/08/2022 Reusable object for buttons! 
public class MenuPageController implements Initializable {
    private static IntegerProperty coins = new SimpleIntegerProperty();
    private static IntegerProperty coinBalance = new SimpleIntegerProperty();

    private static ObservableMap<String,Integer> levelInfo = FXCollections.observableHashMap();

    private static Supplier<Integer> coinSupplier;
    private static Supplier<HashMap<String,Integer>> levelInfoSupplier;
    private static View context;

    @FXML Label coinLabel;
    @FXML Label levelLabel;
    @FXML ProgressBar levelProgressBar;

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
        // Set Coin Info
        InvalidationListener coinListener = e -> {
            coinLabel.setText(String.valueOf(coins.get()));
        };
        coins.addListener(coinListener);
        setCoins();

        // Set Level Info
        InvalidationListener levelListener =  e -> {
            levelLabel.setText(String.valueOf(levelInfo.get("Level")));
            if (levelInfo.get("Level") == 9)
                levelProgressBar.setProgress(1.0);
            else {
                double total = levelInfo.get("LevelCap") - levelInfo.get("OldLevelCap");
                double progress = levelInfo.get("CoinTotal") - levelInfo.get("OldLevelCap");
                levelProgressBar.setProgress(progress / total);
            }
        };
        coinBalance.addListener(levelListener);
        setLevelInfo();

        // Navigate to Habit Page
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

    /**
     * Clear old level info map and provide new data
     */
    public static void setLevelInfo() {
        HashMap<String,Integer> newLevelMap = levelInfoSupplier.get();
        levelInfo.clear();
        for (Map.Entry<String,Integer>  entry : newLevelMap.entrySet() ) {
            levelInfo.put(entry.getKey(), entry.getValue());
        }
        coinBalance.setValue(levelInfo.get("CoinTotal"));
        System.out.println(levelInfo.get("Level"));
        System.out.println(levelInfo.get("LevelCap"));
        System.out.println(levelInfo.get("OldLevelCap"));
        System.out.println(levelInfo.get("CoinTotal"));
    }

    /**
     * Set supplier of level info in Controller
     * @param supplier New supplier
     */
    public static void setLevelInfoSupplier(Supplier<HashMap<String,Integer>> supplier) {
        levelInfoSupplier = supplier;
    }
}
