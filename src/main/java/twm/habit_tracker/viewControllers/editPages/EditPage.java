package twm.habit_tracker.viewControllers.editPages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import twm.habit_tracker.viewControllers.data.ModelData;
import twm.habit_tracker.viewControllers.mainPages.Message;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class for composing edit box
 * and holding code for functions
 */
public class EditPage {
    private static final Integer HALF = 2;

    private static Function<String[], String> addEntryFunction;
    private static BiFunction<String[], String, String> editEntryFunction;
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
     * @param buttonBarLocation Edit or Add Button
     * @param inputFieldsLocation Habit or Goal Button
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
        InputAbstractController.setInputData(inputData);
        buildEditPage(buttonBarLocation, inputFieldsLocation);
    }

    public void add() {
        String output = addEntryFunction.apply(inputFieldsController.getFields());
        if (output.charAt(0) == '!')
            Message.display(output.substring(1));
        else
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
        String output = editEntryFunction.apply(inputFieldsController.getFields(), key);
        if (output.charAt(0) == '!')
            Message.display(output.substring(1));
        else
            buttonController.backButtonPush();
    }

    public static void setAddEntryFunction(Function<String [], String> addEntryFunctionInput ) {
        addEntryFunction = addEntryFunctionInput;
    }

    public static void setDeleteEntryConsumer(Consumer<String> deleteEntryConsumerInput ) {
        deleteEntryConsumer = deleteEntryConsumerInput;
    }

    public static void setEditEntryFunction(BiFunction<String[], String, String> editEntryFunctionInput) {
        editEntryFunction = editEntryFunctionInput;
    }

}
