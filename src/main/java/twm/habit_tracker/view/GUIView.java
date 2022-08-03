package twm.habit_tracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import twm.habit_tracker.view.mainPages.MenuPage;

import java.io.IOException;
import java.util.HashMap;

public class GUIView implements View {
    private Parent menu_page;
    HashMap<String, Node> pages = new HashMap<>();

    @Override
    public Node getPage(String pageID) {
        return pages.get(pageID);
    }

    @Override
    public void setUp(Stage primaryStage) {
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.DECORATED);

        // Set Context!
        MenuPage.setContext(this);

        // Load Pages!
        try {
            pages.put("Habit", FXMLLoader.load(GUIView.class.getResource("HabitPage.fxml")));
            pages.put("Goal", FXMLLoader.load(GUIView.class.getResource("GoalPage.fxml")));

            menu_page = FXMLLoader.load(GUIView.class.getResource("MenuPage.fxml"));
            // Can add other pages as needed!
        } catch (IOException e) {
            System.out.println("Problems when loading FXML File");
            e.printStackTrace();
        }


        Scene scene = new Scene(menu_page); // Width, then Height
        primaryStage.setScene(scene);

        primaryStage.show();

    }
}
