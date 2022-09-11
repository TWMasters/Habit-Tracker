package twm.habit_tracker.view.mainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.*;

public class AvatarPageController implements Initializable {
    private static String NO_REWARD = "None";
    private static String[] REWARD_TYPES = {"Headwear", "Eyewear", "Outer-layer", "Under-layer", "Bottoms", "Footwear"};

    private static HashMap<String, ObservableList<String>> rewardMap = new HashMap<>();
    private static Runnable rewardMapRunnable;

    @FXML ChoiceBox headBox;
    @FXML ChoiceBox eyeBox;
    @FXML ChoiceBox outerBox;
    @FXML ChoiceBox innerBox;
    @FXML ChoiceBox bottomBox;
    @FXML ChoiceBox footBox;



    private void buildMap() {
        for (String rewardType : REWARD_TYPES) {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add(NO_REWARD);
            rewardMap.put(rewardType, list);
        }
        setRewardMap();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildMap();
        for (Map.Entry<String, ObservableList<String>> e  : rewardMap.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue());
        setChoiceBoxes();

    }

    public static HashMap<String, ObservableList<String>> getRewardMap() {
        return rewardMap;
    }

    public void setChoiceBox(ChoiceBox<String> choiceBox, String key) {
        ObservableList<String> options = rewardMap.get(key);
        String currentValue = choiceBox.getValue();
        choiceBox.setItems(options);
        choiceBox.setValue(currentValue);
    }

    /**
     * Helper method to set all choice boxes at the start
     */
    private void setChoiceBoxes() {
        setChoiceBox(headBox, "Headwear");
        setChoiceBox(eyeBox, "Eyewear");
        setChoiceBox(outerBox, "Outer-layer");
        setChoiceBox(innerBox, "Under-layer");
        setChoiceBox(bottomBox, "Bottoms");
        setChoiceBox(footBox, "Footwear");
    }

    public void setRewardMap() {
        rewardMapRunnable.run();
    }

    public static void setRewardMapRunnable(Runnable r) {
        rewardMapRunnable = r;
    }
}
