package twm.habit_tracker.viewControllers.mainPages;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;

// TODO: 26/08/2022 Reusable object for buttons! 
public class MenuPageController implements Initializable {
    private static IntegerProperty coins = new SimpleIntegerProperty();
    private static IntegerProperty coinTotal = new SimpleIntegerProperty();
    private static IntegerProperty level = new SimpleIntegerProperty();

    private static ObservableMap<String,Integer> levelInfo = FXCollections.observableHashMap();

    private static Supplier<Integer> coinSupplier;
    private static Supplier<HashMap<String,Integer>> levelInfoSupplier;
    private HashMap<String, Node> pages = new HashMap<>();

    @FXML Label coinLabel;
    @FXML Label levelLabel;
    @FXML ProgressBar levelProgressBar;

    @FXML private BorderPane mainFrame;


    /**
     * Load Avatar Page
     */
    public void avatarButtonPush() {
        buttonHelper("Avatar");
    }

    /**
     * Helper method to load a page
     * @param pageName Page Key
     */
    private void buttonHelper(String pageName)  {
        Node page = pages.get(pageName);
        mainFrame.setCenter(page);
        BorderPane.setAlignment(page, Pos.TOP_LEFT);
    }

    /**
     * Exit Application
     */
    public void exitButtonPush() {
        Stage currentWindow = (Stage) mainFrame.getScene().getWindow();
        currentWindow.close();
    }

    /**
     * Load Goal Page
     */
    public void goalButtonPush() {
        buttonHelper("Goal");
    }

    /**
     * Load Habit Page
     */
    public void habitButtonPush() {
        buttonHelper("Habit");
    }

    /**
     * Load Trophy Page
     */
    public void trophyButtonPush() {
        TrophyPageController.setTrophyData();
        buttonHelper("Trophy");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set Pages
        setPages();

        // Set Coin Info
        InvalidationListener coinListener = e -> {
            coinLabel.setText(String.valueOf(coins.get()));
        };
        coins.addListener(coinListener);
        setCoins();

        // Set Level Info
        coinTotal.setValue(-1); // Reset to force change!
        ChangeListener levelListener = (observableValue, oldValue, newValue) -> {
            System.out.println("Level Listener!");
            levelLabel.setText(String.valueOf(levelInfo.get("Level")));
            if (levelInfo.get("Level") == 9)
                levelProgressBar.setProgress(1.0);
            else {
                double total = levelInfo.get("LevelCap") - levelInfo.get("OldLevelCap");
                double progress = levelInfo.get("CoinTotal") - levelInfo.get("OldLevelCap");
                levelProgressBar.setProgress(progress / total);
            }
        };
        coinTotal.addListener(levelListener);
        setLevelInfo();

        // Level Messenger
        ChangeListener levelMessageListener = (obs,  oldValue, newValue) -> {
            Message.display("CONGRATULATIONS!\nYou have reached level " + level.getValue());
        };
        level.addListener(levelMessageListener);

        // Navigate to Habit Page
        habitButtonPush();

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
        for (Map.Entry<String,Integer>  entry : newLevelMap.entrySet() ) {
            levelInfo.put(entry.getKey(), entry.getValue());
        }
        coinTotal.setValue(levelInfo.get("CoinTotal"));
        level.setValue(levelInfo.get("Level"));
    }

    /**
     * Set supplier of level info in Controller
     * @param supplier New supplier
     */
    public static void setLevelInfoSupplier(Supplier<HashMap<String,Integer>> supplier) {
        levelInfoSupplier = supplier;
    }

    private void setPages() {

        try {
            pages.put("Habit", FXMLLoader.load(MenuPageController.class.getResource("HabitPage.fxml")));
            pages.put("Goal", FXMLLoader.load(MenuPageController.class.getResource("GoalPage.fxml")));
            pages.put("Trophy", FXMLLoader.load(MenuPageController.class.getResource("TrophyPage.fxml")));
            pages.put("Avatar", FXMLLoader.load(MenuPageController.class.getResource("AvatarPage.fxml")));

        }
        catch (IOException e) {
            System.out.println("Problems when loading FXML File");
            e.printStackTrace();
        }



    }
}
