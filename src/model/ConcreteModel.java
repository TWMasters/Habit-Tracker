package model;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import view.View;

public class ConcreteModel implements Model {
    // Transfer this constant to a file?
    private static final String HABIT_TABLE_SQL =
            "CREATE TABLE Habits (\n" +
                    "  Habit_ID  INT PRIMARY KEY,\n" +
                    "  Habit_Name VARCHAR(255) NOT NULL,\n" +
                    "  Binary_Habit BOOLEAN,\n" +
                    "  Habit_Question VARCHAR(255) NOT NULL,\n" +
                    "  Unit VARCHAR(255),\n" +
                    "  Target NUMERIC(18,2)\n" +
                    ");";
    private static final String SELECT_ALL =
            "SELECT * FROM Habits;";
    private static final String URL = "jdbc:h2:./db/Habits";

    private Connection connection = null;
    private static Model model = null;
    private ArrayList<View> views = new ArrayList();

    private ConcreteModel() {
        try {
            Boolean dbExists = new File("db/Habits.mv.db").exists();
            connection = DriverManager.getConnection(URL);
            if (!dbExists) createTables();

        } catch (SQLException e) {
            System.err.println("SQL Exception");
            e.printStackTrace();
        }
    }

    @Override
    public void addView(View view) {
        views.add(view);
    }

    // Run SQL Command to create Habit Table and commit
    private void createTables() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(HABIT_TABLE_SQL);
    }

    /**
     * Singleton pattern so no more than one instance of Database
     * Please make getModel static and Constructor private on implementation
     * @return concrete instance of Model
     */
    public static Model getModel() {
        if (model == null)
            model = new ConcreteModel();
        return model;
    }

    @Override
    public void notifyViews() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet context = stmt.executeQuery(SELECT_ALL);
            for (View v : views)
                v.update(context);
        } catch (SQLException e) {
            System.err.println("SQL Exception");
            e.printStackTrace();
        }
    }
}
