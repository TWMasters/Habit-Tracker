package twm.habit_tracker.view;

import javafx.beans.property.*;

import java.util.Date;

public class Goal extends ModelData {

    private StringProperty goal = new SimpleStringProperty();
    private StringProperty goalDescription = new SimpleStringProperty();
    private ObjectProperty<Date> date = new SimpleObjectProperty<>();
    private BooleanProperty achieved = new SimpleBooleanProperty();

    public String getGoal() {
        return goal.get();
    }

    public StringProperty goalProperty() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal.set(goal);
    }

    public String getGoalDescription() {
        return goalDescription.get();
    }

    public StringProperty goalDescriptionProperty() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription.set(goalDescription);
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public boolean isAchieved() {
        return achieved.get();
    }

    public BooleanProperty achievedProperty() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved.set(achieved);
    }

    @Override
    public String[] getAllFields() {
        String [] output = {
                getPrimaryKey(),
                getGoal(),
                getGoalDescription(),
                String.valueOf(getDate()),
                String.valueOf(isAchieved())
        };
        return output;
    }
}
