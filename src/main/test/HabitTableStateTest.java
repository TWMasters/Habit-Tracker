import org.junit.jupiter.api.*;
import twm.habit_tracker.model.ConcreteModel;
import twm.habit_tracker.model.HabitTableState;
import twm.habit_tracker.model.Model;

import java.sql.*;

/**
 * Tests assume Habit Database exists to work
 */
public class HabitTableStateTest {
    private static final String H2_URL = "jdbc:h2:./db/Habits";

    private static Connection connection;
    private static int primaryKey;
    private static Model testModel;
    private static Statement stmt;

    @BeforeAll
    static void setup() {
        try {
            connection = DriverManager.getConnection(H2_URL);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(Habit_ID) FROM Habits;");

            if (rs.isBeforeFirst()) {
                rs.next();
                primaryKey = rs.getInt(1) + 1;
            }
            else primaryKey = 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        testModel = ConcreteModel.getModel();
        testModel.changeTargetTable(new HabitTableState());

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
            stmt.execute("DELETE FROM Habits " +
                    "WHERE Habit_ID = " + primaryKey + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetRow() {
        try {
            stmt.execute("INSERT INTO Habits" +
                    " VALUES (" + primaryKey + ",\'TestHabit1\', true, \'Have you done TestHabit1?\', null, null);");
            ResultSet rs = testModel.getEntry(String.valueOf(primaryKey));
            if (rs != null) {
                rs.next();
                Assertions.assertEquals(String.valueOf(primaryKey), rs.getString(1));
                Assertions.assertEquals("TestHabit1", rs.getString(2));
                Assertions.assertEquals("Have you done TestHabit1?", rs.getString(4));
            }
            Assertions.assertNotNull(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testAddRow() {
        String[] input = {"TestHabit2", "true", "Have you done TestHabit2?", "null", "null"};
        try {
            testModel.addEntry(input);
            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_ID = " + primaryKey + ";");

            if (rs.isBeforeFirst()) {
                rs.next();
                Assertions.assertEquals("TestHabit2", rs.getString(2));
                Assertions.assertEquals("Have you done TestHabit2?", rs.getString(4));
            }
            else
                Assertions.fail("Added Row could not be found!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.execute("ALTER TABLE Habit_Tracker DROP COLUMN TestHabit2");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testDeleteRow() {
        try {
            stmt.execute("INSERT INTO Habits" +
                    " VALUES (" + primaryKey + ",\'TestHabit4\', true, \'Have you done TestHabit4?\', null, null);");
            stmt.execute("ALTER TABLE Habit_Tracker ADD TestHabit4 BOOLEAN DEFAULT false;");
            testModel.deleteEntry(String.valueOf(primaryKey));
            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_ID = " + primaryKey + ";");
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
            stmt.execute("INSERT INTO Habits VALUES(" + primaryKey + ", \'TestHabit3\', true, \'Have you done TestHabit3?\', null, null)");
            String[] newValues = {"TestHabit3Edited", "true", "Have you done TestHabit3Edited?", "null", "null"};
            testModel.editEntry(newValues, String.valueOf(primaryKey));

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_ID = " + primaryKey + ";");
            rs.next();
            Assertions.assertEquals("TestHabit3Edited", rs.getString(2));
            Assertions.assertEquals("Have you done TestHabit3Edited?", rs.getString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
