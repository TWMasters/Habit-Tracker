package twm.habit_tracker.view;

import javafx.beans.property.*;

public class Habit extends ModelData {

    private StringProperty habit = new SimpleStringProperty();
    private StringProperty habitQuestion = new SimpleStringProperty();
    private BooleanProperty habitType = new SimpleBooleanProperty();
    private StringProperty unit = new SimpleStringProperty();
    private DoubleProperty target = new SimpleDoubleProperty();

    public String getHabit() {
        return habit.get();
    }

    public void setHabit(String habit) {
        this.habit.set(habit);
    }

    public String getHabitQuestion() {
        return habitQuestion.get();
    }

    public boolean isHabitType() {
        return habitType.get();
    }

    public BooleanProperty habitTypeProperty() {
        return habitType;
    }

    public void setHabitType(boolean habitType) {
        this.habitType.set(habitType);
    }

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public double getTarget() {
        return target.get();
    }

    public DoubleProperty targetProperty() {
        return target;
    }

    public void setTarget(double target) {
        this.target.set(target);
    }

    public void setHabitQuestion(String habitQuestion) {
        this.habitQuestion.set(habitQuestion);
    }

    @Override
    public String[] getAllFields() {
        String[] output = {getPrimaryKey(), getHabit(), getHabitQuestion()};
        return output;
    }
}
