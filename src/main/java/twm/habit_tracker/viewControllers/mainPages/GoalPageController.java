package twm.habit_tracker.viewControllers.mainPages;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import twm.habit_tracker.viewControllers.data.Goal;
import twm.habit_tracker.viewControllers.editPages.EditPage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GoalPageController implements Initializable {

    private EditPage editPage;
    private EventHandler<ActionEvent> editButtonPush;
    private static Consumer<String> markGoalAsCompleteConsumer;
    private static Function<String, Goal> getGoalEntryFunction;
    private static ObservableList<Goal> goalDataSet;
    private static Supplier<ObservableList<Goal>> goalDataSupplier;

    @FXML private GridPane Goals_Container;
    @FXML private GridPane Goals_Container_Fin;

    {
        goalDataSet = FXCollections.observableArrayList();
        editButtonPush = e -> {
            Button buttonClicked = (Button) e.getTarget();
            String id = buttonClicked.getId().substring(6);
            Goal g = getGoalEntryFunction.apply(id);
            editPage = new EditPage("EditButtons.fxml", "GoalInputFields.fxml", g);
            editPage.display("GOAL");
            getGoalData();
            buildGoalContainer();
        };

    }

    /**
     * Method for when edit button is pressed
     */
    public void addButtonPush() {
        editPage = new EditPage("AddButtons.fxml", "GoalInputFields.fxml");
        editPage.display("GOAL");
        getGoalData();
        buildGoalContainer();
    }

    /**
     * Helper method to place goal in correct container
     */
    private int buildGoalsContainerHelper(int rowCount, GridPane gridPane, Node[] nodes) {
        GridPane.setConstraints(nodes[0], 0, rowCount, 1, 1);
        GridPane.setConstraints(nodes[1], 1, rowCount, 1,  1);
        gridPane.getChildren().addAll(nodes);
        return rowCount + 1;
    }

    private void buildGoalContainer() {
        Goals_Container.getChildren().clear();
        Goals_Container_Fin.getChildren().clear();
        int row_count_ongoing = 0;
        int row_count_completed = 0;
        for (Goal g: goalDataSet) {
            Button button = new Button(g.getGoal());
            button.setAlignment(Pos.CENTER);
            button.setPrefSize(288, 60);
            button.setStyle("-fx-font-size: 18");
            button.setId("button" + g.getPrimaryKey());
            button.setOnAction(editButtonPush);


            CheckBox checkBox = new CheckBox();
            checkBox.setPrefSize(72,  72);
            checkBox.setId("checkBox" + g.getPrimaryKey());

            // Check if goal ticked
            if (g.isAchieved()) {
                checkBox.setSelected(true);
                checkBox.setDisable(true);
            }
            // Function to check box
            checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                String goalID = checkBox.getId().substring(8);
                markGoalAsCompleteConsumer.accept(goalID);
                checkBox.setDisable(true);
                getGoalData();
                buildGoalContainer();
            });

            // Add to correct section
            Node[] nodes = {button, checkBox};
            if (g.isAchieved())
                row_count_completed = buildGoalsContainerHelper(row_count_completed, Goals_Container_Fin, nodes);
            else
                row_count_ongoing = buildGoalsContainerHelper(row_count_ongoing, Goals_Container, nodes);
        }

    }

    public void getGoalData() {
        goalDataSet = goalDataSupplier.get();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getGoalData();
        buildGoalContainer();

    }

    public static void setGetGoalEntryFunction(Function<String, Goal> function) {
        GoalPageController.getGoalEntryFunction = function;
    }

    public static void setGoalDataSupplier(Supplier<ObservableList<Goal>> supplier) {
        goalDataSupplier = supplier;
    }

    public static void setMarkGoalAsCompleteConsumer(Consumer<String> consumer) {
        markGoalAsCompleteConsumer = consumer;
    }
}
