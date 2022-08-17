package twm.habit_tracker.view.editPages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import twm.habit_tracker.view.ModelData;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EditPage {
    private static final Integer HALF = 2;

    private static Consumer<String[]> addEntryConsumer;
    private static BiConsumer<String[], String> editEntryConsumer;
    private static Consumer<String> deleteEntryConsumer;

    private Parent container;
    private EditPageContainerController containerController;

    private Node buttonBar;
    private ButtonController buttonController;

    private Node inputFields;
    private InputAbstractController inputFieldsController;

    private Stage popupWindow;

    /**
     * Constructor for Adding data to Model
     * @param buttonBarLocation
     * @param inputFieldsLocation
     */
    public EditPage(String buttonBarLocation, String inputFieldsLocation) {
        buildEditPage(buttonBarLocation, inputFieldsLocation);

    }

    /**
     * Constructor when Editing existing data in Model
     * @param buttonBarLocation
     * @param inputFieldsLocation
     * @param inputData
     */
    public EditPage(String buttonBarLocation, String inputFieldsLocation, ModelData inputData) {
        HabitInputFieldsController.setInputData(inputData);
        buildEditPage(buttonBarLocation, inputFieldsLocation);
    }

    public void add() {
        addEntryConsumer.accept(inputFieldsController.getFields());
        buttonController.backButtonPush();
    }

    public void back() {
        InputAbstractController.setInputData(null);
        popupWindow.close();
    }

    /**
     * Helper method to build Edit Page
     * @param buttonBarLocation
     * @param inputFieldsLocation
     */
    private void buildEditPage(String buttonBarLocation, String inputFieldsLocation) {
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

    public void delete() {
        String key = inputFieldsController.getInputData().getPrimaryKey();
        deleteEntryConsumer.accept(key);
        buttonController.backButtonPush();
    }

    public void display (String title) {
        popupWindow = new Stage();
        popupWindow.initStyle(StageStyle.UNDECORATED);

        // Set up Stage
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);

        Scene scene = new Scene(container, 800, 600); // Width, then Height
        popupWindow.setScene(scene);
        popupWindow.showAndWait();

    }

    public void save() {
        String key = inputFieldsController.getInputData().getPrimaryKey();
        editEntryConsumer.accept(inputFieldsController.getFields(), key);
        buttonController.backButtonPush();
    }

    public static void setAddEntryConsumer(Consumer<String []> addEntryConsumerInput ) {
        addEntryConsumer = addEntryConsumerInput;
    }

    public static void setDeleteEntryConsumer(Consumer<String> deleteEntryConsumerInput ) {
        deleteEntryConsumer = deleteEntryConsumerInput;
    }

    public static void setEditEntryConsumer(BiConsumer<String[], String> editEntryConsumerInput) {
        editEntryConsumer = editEntryConsumerInput;
    }

}
