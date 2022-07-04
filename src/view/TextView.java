package view;

import controller.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TextView implements View {
    private final Controller controller;

    public TextView(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void getData() {
        controller.getData();
    }

    @Override
    public void render() {
        System.out.println("HABIT TRACKER");
        getData();
    }

    @Override
    public void update(ResultSet context) {
        try {
            System.out.print(context.getMetaData().getColumnName(2) + "  ");
            System.out.println(context.getMetaData().getColumnName(4) + "  ");
            while  (context.next()) {
                System.out.print(context.getString("Habit_Name")+ "  ");
                System.out.println(context.getString("Habit_Question"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
