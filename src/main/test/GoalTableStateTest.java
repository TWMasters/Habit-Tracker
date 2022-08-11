import org.junit.jupiter.api.*;
import twm.habit_tracker.model.ConcreteModel;
import twm.habit_tracker.model.GoalTableState;
import twm.habit_tracker.model.Model;

import java.sql.*;

/**
 * Tests assume Habit Database exists to work
 */

public class GoalTableStateTest {
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
            ResultSet rs = stmt.executeQuery("SELECT MAX(Goal_ID) FROM Goals;");
            if (rs.isBeforeFirst()) {
                rs.next();
                primaryKey = rs.getInt(1) + 1;
            }
            else primaryKey = 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        testModel = ConcreteModel.getModel();
        testModel.changeTargetTable(new GoalTableState());

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
            stmt.execute("DELETE FROM Goals " +
                    "WHERE Goal_ID = " + primaryKey + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetRow() {
        try {
            stmt.execute("INSERT INTO Goals" +
                    " VALUES (" + primaryKey + ",\'TestGoal1\', null, null, false);");
            ResultSet rs = testModel.getEntry(String.valueOf(primaryKey));
            if (rs != null) {
                rs.next();
                Assertions.assertEquals(String.valueOf(primaryKey), rs.getString(1));
                Assertions.assertEquals("TestGoal1", rs.getString(2));
            }
            Assertions.assertNotNull(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testAddRow() {
        String[] input = {"TestGoal2", "null", "null"};
        try {
            testModel.addEntry(input);
            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_ID = " + primaryKey + ";");
            if (rs.isBeforeFirst()) {
                rs.next();
                Assertions.assertEquals("TestGoal2", rs.getString(2));
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
            stmt.execute("INSERT INTO Goals" +
                    " VALUES (" + primaryKey + ",\'TestGoal4\', null, null, false);");
            testModel.deleteEntry(String.valueOf(primaryKey));
            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_ID = " + primaryKey + ";");
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
            stmt.execute("INSERT INTO Goals VALUES(" + primaryKey + ", \'TestGoal3\', null, null, false)");
            String[] newValues = {"TestGoal3Edited", "null", "null", "false"};
            testModel.editEntry(newValues, String.valueOf(primaryKey));

            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_ID = " + primaryKey + ";");
            rs.next();
            Assertions.assertEquals("TestGoal3Edited", rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
