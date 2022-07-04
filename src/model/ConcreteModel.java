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
    private static final String URL = "jdbc:h2:./db/Habits";

    private Connection connection = null;
    private static Model model = null;
    private ArrayList<View> views = new ArrayList();

    private ConcreteModel() {
        try {
            // 1 - Check if DB file exists. If not, set flag to false
            Boolean dbExists = new File("db/Habits.mv.db").exists();
            // 2 - Connect/ Create DB
            System.out.println("Connecting to Database");
            connection = DriverManager.getConnection(URL);
            // 3 - If flag false, create tables
            if (!dbExists) {
                Statement stmt = connection.createStatement();
                stmt.execute(HABIT_TABLE_SQL);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            e.printStackTrace();
        }
    }

    // Run SQL Command to create Habit Table and commit
    private void createTables() {

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
    public void notify(String context) {

    }
}
