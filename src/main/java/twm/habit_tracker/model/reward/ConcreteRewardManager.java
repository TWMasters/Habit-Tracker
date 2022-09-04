package twm.habit_tracker.model.reward;

import java.sql.Connection;
import java.sql.ResultSet;

public class ConcreteRewardManager implements RewardManager {
    private final Connection connection;
    private TrophyTable trophyTable;

    public ConcreteRewardManager(Connection connection) {
        this.connection = connection;
        trophyTable = new TrophyTable(connection);
    }


    @Override
    public void buildTables() {
        trophyTable.createTrophyTable();
    }

    @Override
    public ResultSet checkTrophies() {
        return null;
    }

    @Override
    public void getCoins() {

    }

    @Override
    public void changeBalance(int coins) {

    }

    @Override
    public void updateTables() {
        trophyTable.updateDates();
    }
}
