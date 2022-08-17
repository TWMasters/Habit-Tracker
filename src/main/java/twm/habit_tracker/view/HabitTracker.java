package twm.habit_tracker.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HabitTracker extends ModelData {

    private IntegerProperty target = new SimpleIntegerProperty();
    private StringProperty completed = new SimpleStringProperty();

    public int getTarget() {
        return target.get();
    }

    public IntegerProperty targetProperty() {
        return target;
    }

    public void setTarget(int target) {
        this.target.set(target);
    }

    public String getCompleted() {
        return completed.get();
    }

    public StringProperty completedProperty() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed.set(completed);
    }

    @Override
    public String[] getAllFields() {
        String[] output = {
                getPrimaryKey(),
                String.valueOf(getTarget()),
                getCompleted()
        };
        return output;
    }
}
