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
    private static int[] primaryKeys = new int[3];
    private static Model testModel;

    @BeforeAll
    static void setup() {
        try {
            connection = DriverManager.getConnection(H2_URL);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(Habit_ID) FROM Habits;");
            rs.next();
            int highestID = rs.getInt(1) + 1;
            for (int i = 0; i < 2; i++)
                primaryKeys[i] = highestID + i;
            // Add SQL
            stmt.execute("INSERT INTO Habits" +
                    " VALUES (" + primaryKeys[0] + ",\'TestHabit1\', true, \'Have you done TestHabit1?\', null, null);");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        testModel = ConcreteModel.getModel();
        testModel.changeTargetTable(new HabitTableState());

    }

    @AfterAll
    static void shutDown() {
        testModel.closeConnection();
        // Write SQL to ensure added rows have been deleted
        try {
            connection = DriverManager.getConnection(H2_URL);
            Statement stmt = connection.createStatement();
            stmt.execute("DELETE FROM Habits " +
                    "WHERE Habit_ID = " + primaryKeys[0] + ";");
            stmt.execute("DELETE FROM Habits " +
                    "WHERE Habit_ID = " + primaryKeys[1] + ";");
            stmt.execute("DELETE FROM Habits " +
                    "WHERE Habit_ID = " + primaryKeys[2] + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testGetRow() {
        ResultSet rs = testModel.getRow(String.valueOf(primaryKeys[0]));
        try {
            if (rs != null) {
                rs.next();
                Assertions.assertEquals(String.valueOf(primaryKeys[0]), rs.getString(1));
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
            // testModel.closeConnection();
            connection = DriverManager.getConnection(H2_URL);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Habits WHERE Habit_ID = " + primaryKeys[1] + ";");
            if (rs.isBeforeFirst()) {
                Assertions.assertEquals("TestHabit2", rs.getString(2));
                Assertions.assertEquals("Have you done TestHabit2?", rs.getString(4));
            }
            else
                Assertions.fail("Row not added!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
