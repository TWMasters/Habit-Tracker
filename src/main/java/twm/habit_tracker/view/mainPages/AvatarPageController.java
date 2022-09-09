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

    private static HashMap<String, ObservableList<String>> rewardMap;
    private static Runnable rewardMapRunnable;

    @FXML ChoiceBox headBox;
    @FXML ChoiceBox eyeBox;
    @FXML ChoiceBox outerBox;
    @FXML ChoiceBox innerBox;
    @FXML ChoiceBox bottomBox;
    @FXML ChoiceBox footBox;

    {
        rewardMap.put("Headwear", FXCollections.observableList(List.of(NO_REWARD)));
        rewardMap.put("Eyewear", FXCollections.observableList(List.of(NO_REWARD)));
        rewardMap.put("Outer-layer", FXCollections.observableList(List.of(NO_REWARD)));
        rewardMap.put("Inner-layer", FXCollections.observableList(List.of(NO_REWARD)));
        rewardMap.put("Bottoms", FXCollections.observableList(List.of(NO_REWARD)));
        rewardMap.put("Footwear", FXCollections.observableList(List.of(NO_REWARD)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public HashMap<String, ObservableList<String>> getRewardMap() {
        return rewardMap;
    }

    public void setChoiceBox(ChoiceBox<String> choiceBox, String key) {
        ObservableList<String> options = rewardMap.get(key);
        String currentValue = choiceBox.getValue();
        choiceBox.setItems(options);
        choiceBox.setValue(currentValue);
    }

    public void setChoiceBoxes() {
        setChoiceBox(headBox, "Headwear");
        setChoiceBox(headBox, "Eyewear");
        setChoiceBox(headBox, "Outer-layer");
        setChoiceBox(headBox, "Inner-layer");
        setChoiceBox(headBox, "Bottoms");
        setChoiceBox(headBox, "Footwear");
    }

    public void setRewardMap() {
        rewardMapRunnable.run();
    }

    public void setRewardMapSupplier(Runnable r) {
        rewardMapRunnable = r;
    }
}
