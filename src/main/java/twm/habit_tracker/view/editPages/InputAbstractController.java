package twm.habit_tracker.view.editPages;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import twm.habit_tracker.view.ModelData;

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
    public String getNullableValue(TextField inputField) {
        String output = inputField.getText();
        if (output.equals(""))
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
