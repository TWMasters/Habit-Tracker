package twm.habit_tracker.viewControllers.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Trophy extends ModelData{
    private BooleanProperty trophyWon = new SimpleBooleanProperty();

    public boolean isTrophyWon() {
        return trophyWon.get();
    }

    public BooleanProperty trophyWonProperty() {
        return trophyWon;
    }

    public void setTrophyWon(boolean trophyWon) {
        this.trophyWon.set(trophyWon);
    }

    @Override
    public String[] getAllFields() {
        String[] output = {
                getPrimaryKey(),
                String.valueOf(isTrophyWon())
        } ;
        return output;
    }
}
