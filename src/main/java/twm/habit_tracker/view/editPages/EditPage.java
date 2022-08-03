package twm.habit_tracker.view.editPages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EditPage {
    public static final Integer HALF = 2;

    private Parent container;
    private EditPageContainerController containerController;

    private Node buttonBar;
    private Node inputFields;

    public EditPage(String buttonBar, String inputFields) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();

            // Container
            container = fxmlLoader.load(EditPage.class.getResource("EditPageContainer.fxml"));
            containerController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void display (String title) {
        Stage popupWindow = new Stage();
        popupWindow.initStyle(StageStyle.DECORATED);

        // Set up Stage
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setMinHeight(Screen.getPrimary().getBounds().getHeight() / HALF);
        popupWindow.setMinWidth(Screen.getPrimary().getBounds().getWidth() / HALF);
        popupWindow.setTitle(title);

        Scene scene = new Scene(container); // Width, then Height
        popupWindow.setScene(scene);
        popupWindow.showAndWait();

    }
}
