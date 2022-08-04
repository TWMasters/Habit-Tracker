package twm.habit_tracker.view.editPages;

import javafx.fxml.Initializable;
import twm.habit_tracker.view.ModelData;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class InputAbstractController implements Initializable {
    private static ModelData data;
    private static Runnable targetTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        targetTable.run();
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
     * Use to set Habit Information if editing an existing Habit
     * @return
     */
    public abstract void setFields();

    public static void setInputData(ModelData inputData) {
        data = inputData;
    }

    public static void setTargetTable(Runnable targetTableInput) {
        targetTable = targetTableInput;
    }

}
