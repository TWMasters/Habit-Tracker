package twm.habit_tracker.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Parent class for data from Model
 */
public class ModelData {
    private StringProperty primaryKey = new SimpleStringProperty();

    public String getPrimaryKey() {
        return primaryKey.get();
    }

    public StringProperty primaryKeyProperty() {
        return primaryKey;
    }

    /**
     * Method
     * @param primaryKey
     */
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey.set(primaryKey);
    };
}
