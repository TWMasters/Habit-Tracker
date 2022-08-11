import org.junit.jupiter.api.BeforeAll;
import twm.habit_tracker.model.ConcreteModel;
import twm.habit_tracker.model.HabitTableState;
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
        testModel.changeTargetTable(new HabitTableState());

    }

}
