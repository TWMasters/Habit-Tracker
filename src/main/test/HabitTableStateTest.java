import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
        Assertions.assertEquals(1,1);

    }

    @Test
    void testAddRow() {

    }
}
