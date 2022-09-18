import org.junit.jupiter.api.*;
import twm.habit_tracker.model.ConcreteModel;
import twm.habit_tracker.model.GoalTableState;
import twm.habit_tracker.model.Model;

import java.sql.*;

/**
 * Tests assume Habit Database exists to work
 */

public class GoalTableStateTest {

    private static final int NANO_TO_MILLI = 1_000_000;
    private static final String DATE_INPUT = "2030-01-01";
    private static final String NEW_DATE_INPUT = "2030-02-01";
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
                    " VALUES (" + primaryKey + ",\'TestGoal1\', null,\'" + DATE_INPUT + "\', false);");
            long startTime = System.nanoTime();
            ResultSet rs = testModel.getEntry(String.valueOf(primaryKey));
            double timeTaken = System.nanoTime() - startTime;
            System.out.println("Goal Get Row Time in MS: " + timeTaken / NANO_TO_MILLI);
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
        String[] input = {"TestGoal2", "null", DATE_INPUT};
        try {
            long startTime = System.nanoTime();
            testModel.addEntry(input);
            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_ID = " + primaryKey + ";");
            double timeTaken = System.nanoTime() - startTime;
            System.out.println("Goal Add Row Time in MS: " + timeTaken / NANO_TO_MILLI);
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
                    " VALUES (" + primaryKey + ",\'TestGoal4\', null, \'" + DATE_INPUT + "\', false);");
            long startTime = System.nanoTime();
            testModel.deleteEntry(String.valueOf(primaryKey));
            double timeTaken = System.nanoTime() - startTime;
            System.out.println("Goal Delete Row Time in MS: " + timeTaken / NANO_TO_MILLI);
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
            stmt.execute("INSERT INTO Goals VALUES(" + primaryKey + ", \'TestGoal3\', null, \'"  + DATE_INPUT + "\', false)");
            String[] newValues = {"TestGoal3Edited", "null", DATE_INPUT, "false"};
            long startTime = System.nanoTime();
            testModel.editEntry(newValues, String.valueOf(primaryKey));
            double timeTaken = System.nanoTime() - startTime;
            System.out.println("Goal Edit Row Time in MS: " + timeTaken / NANO_TO_MILLI);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_ID = " + primaryKey + ";");
            rs.next();
            Assertions.assertEquals("TestGoal3Edited", rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEditEntryNameUnchanged() {
        try {
            stmt.execute("INSERT INTO Goals VALUES(" + primaryKey + ", \'TestGoal4\', null, \'" + DATE_INPUT + "\', false)");
            String[] newValues = {"TestGoal4", "null", NEW_DATE_INPUT, "false"};
            testModel.editEntry(newValues, String.valueOf(primaryKey));

            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_ID = " + primaryKey + ";");
            rs.next();
            Assertions.assertEquals("TestGoal4", rs.getString(2));
            Assertions.assertEquals(NEW_DATE_INPUT, rs.getString(4));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEditEntryIdenticalName() {
        try {
            stmt.execute("INSERT INTO Goals VALUES(" + primaryKey + ", \'TestGoal5\', null, \'" + DATE_INPUT + "\', false)");
            stmt.execute("INSERT INTO Goals VALUES(" + (primaryKey + 1) + ", \'TestGoal6\', null, \'" + DATE_INPUT + "\', false)");
            String[] newValues = {"TestGoal5", "null", DATE_INPUT, "false"};
            String errorMessage = testModel.editEntry(newValues, String.valueOf(primaryKey + 1));

            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_Name = \'TestGoal5\';");
            int count = 0;
            while (rs.next()) {
                count ++;
            }
            Assertions.assertEquals("!Please choose a unique Goal name", errorMessage);
            Assertions.assertEquals(count, 1);
            testModel.deleteEntry(String.valueOf(primaryKey + 1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddEntryIdenticalName() {
        try {
            stmt.execute("INSERT INTO Goals VALUES(" + primaryKey + ", \'TestGoal7\', null, \'" + DATE_INPUT + "\', false)");
            String[] newValues = {"TestGoal7", "null", DATE_INPUT, "false"};
            String errorMessage = testModel.addEntry(newValues);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_Name = \'TestGoal7\';");
            int count = 0;
            while (rs.next()) {
                count ++;
            }
            Assertions.assertEquals("!Please choose a unique Goal name", errorMessage);
            Assertions.assertEquals(count, 1);
            testModel.deleteEntry(String.valueOf(primaryKey + 1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddEntryBlankName() {
        try {
            String[] newValues = {"", "null", DATE_INPUT, "false"};
            String errorMessage = testModel.addEntry(newValues);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Goals WHERE Goal_ID = " + primaryKey + ";");
            Boolean flag = !rs.isBeforeFirst();
            Assertions.assertEquals("!Please enter a Goal Name", errorMessage);
            Assertions.assertEquals(flag, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
