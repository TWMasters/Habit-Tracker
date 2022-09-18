package twm.habit_tracker.viewControllers.editPages;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import twm.habit_tracker.viewControllers.data.ModelData;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class InputAbstractController implements Initializable {
    private static ModelData data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTargetTable();
        if (data != null)
            setFields();
    }


    public void generateListener(TextInputControl txt, int maxLength) {
        txt.textProperty().addListener( (observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                String truncatedText = newValue.substring(0, maxLength);
                txt.setText(truncatedText);
                }
            }
        );
    }

    /**
     * Use to get Habit Information entered by user
     * @return Text Field information
     */
    public abstract String[] getFields();

    public ModelData getInputData() {
        return data;
    }

    /**
     * Helper method to get a Null value from input text
     * @param inputField check whether empty
     * @return null if empty
     */
    public String getNullableValue(String inputField) {
        String output = inputField;
        if (output == null || output.equals(""))
            return "null";
        else
            return output;
    }

    /**
     * Use to set Habit Information if editing an existing Habit
     * @return
     */
    public abstract void setFields();

    public static void setInputData(ModelData inputData) {
        data = inputData;
    }

    public abstract void setTargetTable();

}
