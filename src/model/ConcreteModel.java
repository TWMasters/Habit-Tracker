package model;

import java.util.ArrayList;
import view.View;

public class ConcreteModel implements Model {
    // Transfer this constant to a file?
    private static final String HABIT_TABLE_SQL = "";
    
    private Model model = null;
    private ArrayList<View> views = new ArrayList();

    private ConcreteModel() {
        // 1 - Check if DB file exists. If not, set flag to false
        // 2 - Connect/ Create DB
        // 3 - If flag false, create tables
    }

    // Run SQL Command to create Habit Table and commit
    private void createTables() {

    }

    @Override
    public Model getModel() {
        if (model == null)
            model = new ConcreteModel();
        return model;
    }

    @Override
    public void notify(String context) {

    }
}
