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
        }
    }

    @Test
    void testDeleteRow() {
        try {
            stmt.execute("INSERT INTO Habits" +
                    " VALUES (" + primaryKey + ",\'TestHabit4\', true, \'Have you done TestHabit4?\', null, null);");
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

    @Test
    void testEditEntryNameUnchanged() {
        try {
            stmt.execute("INSERT INTO Habits VALUES(" + primaryKey + ", \'TestHabit5\', true, \'Have you done TestHabit5?\', null, null)");
            String[] newValues = {"TestHabit5", "true", "Have you done TestHabit3Edited?", "null", "null"};
            testModel.editEntry(newValues, String.valueOf(primaryKey));

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_ID = " + primaryKey + ";");
            rs.next();
            Assertions.assertEquals("Have you done TestHabit3Edited?", rs.getString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEditEntryIdenticalName() {
        try {
            stmt.execute("INSERT INTO Habits VALUES(" + primaryKey + ", \'TestHabit6\', true, \'Have you done TestHabit6?\', null, null)");
            stmt.execute("INSERT INTO Habits VALUES(" + (primaryKey + 1) + ", \'TestHabit7\', true, \'Have you done TestHabit7?\', null, null)");
            String[] newValues = {"TestHabit6", "true", "Have you done TestHabit7?", "null", "null"};
            String errorMessage = testModel.editEntry(newValues, String.valueOf(primaryKey  + 1));

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_Name = \'TestHabit6\';");
            int count = 0;
            while (rs.next()) {
                count ++;
            }
            Assertions.assertEquals("!Please choose a unique Habit name", errorMessage);
            Assertions.assertEquals(count, 1);
            testModel.deleteEntry(String.valueOf(primaryKey + 1));

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddEntryIdenticalName() {
        try {
            stmt.execute("INSERT INTO Habits VALUES(" + primaryKey + ", \'TestHabit8\', true, \'Have you done TestHabit8?\', null, null)");
            String[] newValues = {"TestHabit8", "true", "Have you done TestHabit9?", "null", "null"};
            String errorMessage = testModel.addEntry(newValues);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_Name = \'TestHabit8\';");
            int count = 0;
            while (rs.next()) {
                count ++;
            }
            Assertions.assertEquals("!Please choose a unique Habit name", errorMessage);
            Assertions.assertEquals(count, 1);
            testModel.deleteEntry(String.valueOf(primaryKey + 1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddEntryBlankName() {
        try {
            String[] newValues = {"", "true", "Have you done TestHabit10?", "null", "null"};
            String errorMessage = testModel.addEntry(newValues);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_ID = " + primaryKey + ";");
            Boolean flag = !rs.isBeforeFirst();
            Assertions.assertEquals("!Please enter a Habit Name", errorMessage);
            Assertions.assertEquals(flag, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddEntryAnalogueBlankUnit() {
        try {
            String[] newValues = {"Habit11", "false", "Have you done TestHabit10?", "null", "50.4"};
            String errorMessage = testModel.addEntry(newValues);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_ID = " + primaryKey + ";");
            Boolean flag = !rs.isBeforeFirst();
            Assertions.assertEquals("!Unit and Target fields must be completed for Analogue Habits", errorMessage);
            Assertions.assertEquals(flag, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
