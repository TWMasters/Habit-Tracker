package view;

import controller.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
        Scanner in = new Scanner(System.in);
        while(true)  {
            System.out.println("Please select one of the following options: ");
            System.out.println("   a: Add new Habit");
            System.out.println("   e: Edit existing Habit");
            System.out.println("   q: Quit");
            System.out.println("Input: ");
            String input = in.nextLine().toLowerCase();
            if (input.equals("q")) {
                System.out.println("Exiting...");
                break;
            }
            switch (input) {
                case "a":
                    System.out.println("Adding new habit");
                    break;
                case "e":
                    System.out.println("Editing existing habit");
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
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
