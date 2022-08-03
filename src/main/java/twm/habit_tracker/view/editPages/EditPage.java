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
import java.util.function.Consumer;

public class EditPage {
    private static final Integer HALF = 2;

    private static Consumer<String[]> addEntryConsumer;

    private Parent container;
    private EditPageContainerController containerController;

    private Node buttonBar;
    private ButtonController buttonController;

    private Node inputFields;
    private InputAbstractController inputFieldsController;

    public EditPage(String buttonBarLocation, String inputFieldsLocation) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EditPage.class.getResource("EditPageContainer.fxml"));

            // Container
            container = fxmlLoader.load();
            containerController = fxmlLoader.getController();

            // Buttons
            fxmlLoader = new FXMLLoader(EditPage.class.getResource(buttonBarLocation));
            buttonBar = fxmlLoader.load();
            buttonController = fxmlLoader.getController();
            buttonController.setContext(this);

            // Input Fields
            fxmlLoader = new FXMLLoader(EditPage.class.getResource(inputFieldsLocation));
            inputFields = fxmlLoader.load();
            inputFieldsController = fxmlLoader.getController();

            // Wiring
            containerController.setButtons(buttonBar);
            containerController.setInputFields(inputFields);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void add() {
        System.out.println("Add Entry!");
        addEntryConsumer.accept(inputFieldsController.getFields());
        buttonController.backButtonPush();
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

    public static void setAddEntryConsumer(Consumer<String []> addEntryConsumerInput ) {
        addEntryConsumer = addEntryConsumerInput;
    }

}
