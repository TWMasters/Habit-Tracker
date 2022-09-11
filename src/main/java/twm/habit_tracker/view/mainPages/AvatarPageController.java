package twm.habit_tracker.view.mainPages;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AvatarPageController implements Initializable {

    private static final String NO_REWARD = "None";
    private static final String[] REWARD_TYPES = {"Headwear", "Eyewear", "Over-layer", "Under-layer", "Bottoms", "Footwear"};

    private static BiConsumer<String,String> changeStateConsumer;
    private static Function<Integer,String> purchaseTicketFunction;
    private HashMap<ChoiceBox<String>, String> choiceBoxMapping = new HashMap<>();
    private static HashMap<String, ObservableList<String>> rewardMap = new HashMap<>();
    private static Runnable rewardMapRunnable;
    private static Supplier<HashMap<String,String>> avatarStateMapSupplier;


    @FXML ChoiceBox headBox;
    @FXML ChoiceBox eyeBox;
    @FXML ChoiceBox overBox;
    @FXML ChoiceBox underBox;
    @FXML ChoiceBox bottomBox;
    @FXML ChoiceBox footBox;

    private void buildAvatarState() {
        HashMap<String,String> currentState = avatarStateMapSupplier.get();
        for (Map.Entry<ChoiceBox<String>, String> entry : choiceBoxMapping.entrySet() )
            entry.getKey().setValue(currentState.get(entry.getValue()));

    }

    private void buildChoiceBoxMap() {
        choiceBoxMapping.put(headBox,"Headwear");
        choiceBoxMapping.put(eyeBox,"Eyewear");
        choiceBoxMapping.put(overBox, "Over-layer");
        choiceBoxMapping.put(underBox, "Under-layer");
        choiceBoxMapping.put(bottomBox, "Bottoms");
        choiceBoxMapping.put(footBox, "Footwear");
    }

    private void buildRewardMap() {
        for (String rewardType : REWARD_TYPES) {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add(NO_REWARD);
            rewardMap.put(rewardType, list);
        }
        setRewardMap();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildChoiceBoxMap();
        buildRewardMap();
        for (Map.Entry<String, ObservableList<String>> e  : rewardMap.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue());
        setChoiceBoxes();
        buildAvatarState();
    }

    public static HashMap<String, ObservableList<String>> getRewardMap() {
        return rewardMap;
    }

    public static void setAvatarStateMapSupplier(Supplier<HashMap<String,String>> s) {
        avatarStateMapSupplier = s;
    }

    public static void setChangeStateConsumer(BiConsumer<String,String> consumer) {
        changeStateConsumer = consumer;
    }

    /**
     * Set items for an individual choicebox
     * @param choiceBox target choicebox
     * @param key Reward Map Key to retrieve items
     */
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
        for (Map.Entry<ChoiceBox<String>, String> entry : choiceBoxMapping.entrySet()) {
            setChoiceBox(entry.getKey(), entry.getValue());
            ChangeListener<String> choiceBoxListener = (obs, oldValue, newValue) -> {
                changeStateConsumer.accept(entry.getValue(), newValue);
            };
            entry.getKey().getSelectionModel().selectedItemProperty()
                    .addListener(choiceBoxListener);
        }
    }

    public static void setPurchaseTicketFunction(Function<Integer,String> function) {
        purchaseTicketFunction = function;
    }

    /**
     * Run runnable to populate Reward map
     */
    public void setRewardMap() {
        rewardMapRunnable.run();
    }

    /**
     * Retrieve Model method to set Reward Map from Master Controller
     * @param r Runnable
     */
    public static void setRewardMapRunnable(Runnable r) {
        rewardMapRunnable = r;
    }
}
