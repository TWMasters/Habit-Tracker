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
    private static final String DATE_KEY = "\'3000-01-01\'";
    private static final String DATE_KEY_NO_APOS = "3000-01-01";

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
            stmt.execute("INSERT INTO Habit_Tracker " +
                    "VALUES (" + DATE_KEY + ", 3, '5=1');");
            ResultSet rs = testModel.getEntry(DATE_KEY_NO_APOS);
            if (rs != null) {
                rs.next();
                Assertions.assertEquals("3", rs.getString(2));
                Assertions.assertEquals("5=1", rs.getString(3));
            }
            Assertions.assertNotNull(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testAddRow() {
        String[] input = {DATE_KEY_NO_APOS};
        try {
            testModel.addEntry(input);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habit_Tracker WHERE Date = " + DATE_KEY + ";");
            if (rs.isBeforeFirst()) {
                rs.next();
                Assertions.assertEquals(DATE_KEY_NO_APOS, rs.getString(1));
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
            stmt.execute("INSERT INTO Habit_Tracker" +
                    " VALUES (" + DATE_KEY + ", 3, '5=1');");
            testModel.deleteEntry(DATE_KEY_NO_APOS);
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

    // TODO: 20/07/2022 Update test so add a column in Habit_Tracker 
    @Test
    void testEditEntry() {
        try {
            stmt.execute("INSERT INTO Habit_Tracker" +
                    " VALUES (" + DATE_KEY + ", 3, '5=1');");
            String[] newValues = {"4", "5=0"};
            testModel.editEntry(newValues, DATE_KEY_NO_APOS);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habit_Tracker WHERE Date = " + DATE_KEY + ";");
            if (rs.isBeforeFirst()) {
                rs.next();
                Assertions.assertEquals("4", rs.getString(2));
                Assertions.assertEquals("5=0", rs.getString(3));
            }
            else
                Assertions.fail("Edited Row couldn't be found!");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
