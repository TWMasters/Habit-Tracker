package twm.habit_tracker.controller.mainPages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TrophyMessage {

    public static void display(String message) {
        Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setMinWidth(250);

        Label label1 = new Label(message);
        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> alertWindow.close());

        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label1, closeButton);
        layout1.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout1);
        alertWindow.setScene(scene);

        alertWindow.showAndWait(); // Different show method -> Wait for it to be hidden or closed

    }
}
