package twm.habit_tracker.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import twm.habit_tracker.controller.mainPages.MenuPageController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class GUIView implements View {
    private static String CSS = Path.of("src/main/resources/twm/habit_tracker/view/stylesheet.css").toUri().toString();

    private Parent menu_page;
    private HashMap<String, Node> pages = new HashMap<>();


    @Override
    public Node getPage(String pageID) {
        return pages.get(pageID);
    }

    @Override
    public void setUp(Stage primaryStage) {
        // primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.DECORATED);

        // Set Context!
        MenuPageController.setContext(this);

        // Load Pages!
        try {
            pages.put("Habit", FXMLLoader.load(GUIView.class.getResource("HabitPage.fxml")));
            pages.put("Goal", FXMLLoader.load(GUIView.class.getResource("GoalPage.fxml")));
            pages.put("Trophy", FXMLLoader.load(GUIView.class.getResource("TrophyPage.fxml")));
            pages.put("Avatar", FXMLLoader.load(GUIView.class.getResource("AvatarPage.fxml")));

            menu_page = FXMLLoader.load(GUIView.class.getResource("MenuPage.fxml"));

        } catch (IOException e) {
            System.out.println("Problems when loading FXML File");
            e.printStackTrace();
        }

        Scene scene = new Scene(menu_page, 1000, 1200); // Width, then Height
        scene.getStylesheets().add(CSS);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
