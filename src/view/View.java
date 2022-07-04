package view;

import java.sql.ResultSet;

public interface View {

    /**
     * Method to force the Model to call Notify
     * Use to get data when setting up UI
     */
    void getData();

    /**
     * Method to set up UI
     */
    void render();

    /**
     * Method for Model to call to update view
     * @param context
     */
    void update(ResultSet context);
}
