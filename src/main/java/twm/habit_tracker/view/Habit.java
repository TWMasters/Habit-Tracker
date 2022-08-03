package twm.habit_tracker.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Habit extends ModelData {

    private StringProperty habit = new SimpleStringProperty();
    private StringProperty habitQuestion = new SimpleStringProperty();

    public String getHabit() {
        return habit.get();
    }

    public StringProperty habitProperty() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit.set(habit);
    }

    public String getHabitQuestion() {
        return habitQuestion.get();
    }

    public StringProperty habitQuestionProperty() {
        return habitQuestion;
    }

    public void setHabitQuestion(String habitQuestion) {
        this.habitQuestion.set(habitQuestion);
    }
}
