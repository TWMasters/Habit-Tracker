package twm.habit_tracker.view.editPages;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class InputAbstractController implements Initializable {
    private static Runnable targetTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        targetTable.run();
    }

    /**
     * Use to get Habit Information entered by user
     * @return Text Field information
     */
    public abstract String[] getFields();

    public static void setTargetTable(Runnable targetTableInput) {
        targetTable = targetTableInput;
    }

}
