import org.junit.jupiter.api.*;
import twm.habit_tracker.model.ConcreteModel;
import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.HabitTrackerTableState;
import twm.habit_tracker.model.Model;

import java.sql.*;

/**
 * Tests assume Habit Database exists to work
 * Tests assume no Primary Key of 3000-01-01 exists as too far in future
 */
public class HabitTrackerTableStateTest {
    private static final String H2_URL = "jdbc:h2:./db/Habits";
    private static final String DATE_KEY = "3000-01-01";
    private static final String EDITED_DATE_KEY = "4000-01-01";

    private static Connection connection;
    private static Model testModel;
    private static Statement stmt;

    @BeforeAll
    static void setup() {
        try {
            connection = DriverManager.getConnection(H2_URL);
            stmt = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        testModel = ConcreteModel.getModel();
        testModel.changeTargetTable(new HabitTrackerTableState());

    }

    @AfterAll
    static void shutDown() {
        testModel.closeConnection();
        try {
           if (connection != null)
               connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void deleteRow() {
        // Write SQL to ensure added rows have been deleted
        try {
            stmt.execute("DELETE FROM Habit_Tracker " +
                    "WHERE Date = " + DATE_KEY + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetRow() {
        try {
            stmt.execute("INSERT INTO Habit_Tracker" +
                    " VALUES (" + DATE_KEY + ";");
            ResultSet rs = testModel.getEntry(String.valueOf(DATE_KEY));
            if (rs != null) {
                rs.next();
                Assertions.assertEquals(DATE_KEY, rs.getString(1));
            }
            Assertions.assertNotNull(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testAddRow() {
        String[] input = {DATE_KEY};
        try {
            testModel.addEntry(input);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habit_Tracker WHERE Date = " + DATE_KEY + ";");
            if (rs.isBeforeFirst()) {
                rs.next();
                Assertions.assertEquals(DATE_KEY, rs.getString(1));
            }
            else
                Assertions.fail("Added Row could not be found!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDeleteRow() {
        try {
            stmt.execute("INSERT INTO Habits" +
                    " VALUES (" + DATE_KEY + ");");
            testModel.deleteEntry(DATE_KEY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM Habit_Tracker WHERE Date = " + DATE_KEY + ";");
            if (rs.isBeforeFirst()) {
                Assertions.fail("Entry still exists!");
            }
            else
                return;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEditEntry() {
        try {
            stmt.execute("INSERT INTO Habits" +
                    " VALUES (" + DATE_KEY + ");");
            String[] newValues = {EDITED_DATE_KEY};
            testModel.editEntry(newValues, DATE_KEY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habit_Tracker WHERE Date = " + EDITED_DATE_KEY + ";");
            rs.next();
            Assertions.assertEquals(EDITED_DATE_KEY, rs.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
