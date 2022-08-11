package twm.habit_tracker.view.mainPages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import twm.habit_tracker.view.Goal;
import twm.habit_tracker.view.editPages.EditPage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class GoalPageController implements Initializable {

    private EditPage editPage;
    private static ObservableList<Goal> goalDataSet;
    private static Supplier<ObservableList<Goal>> goalDataSupplier;

    @FXML
    private GridPane Goals_Container;

    {
        goalDataSet = FXCollections.observableArrayList();

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

    private void buildGoalContainer() {
        Goals_Container.getChildren().clear();
        int row_count = 0;
        for (Goal g: goalDataSet) {
            Button button = new Button(g.getGoal());
            button.setAlignment(Pos.CENTER);
            button.setPrefSize(288, 60);
            button.setStyle("-fx-font-size: 18");
            button.setId("goal_button" + g.getPrimaryKey());
            // button.setOnAction();
            GridPane.setConstraints(button, 0, row_count, 1, 1);

            Goals_Container.getChildren().addAll(button);
            row_count++;
        }

    }

    public void getGoalData() {
        goalDataSet = goalDataSupplier.get();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Goal page init!");
        getGoalData();
        buildGoalContainer();

    }

    public static void setGoalDataSupplier(Supplier<ObservableList<Goal>> supplier) {
        goalDataSupplier = supplier;
    }
}
