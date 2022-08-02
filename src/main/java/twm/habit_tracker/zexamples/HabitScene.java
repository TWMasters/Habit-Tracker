package twm.habit_tracker.zexamples;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Class to standardise Scenes for all pages in Habit Tracking app
 */
public class HabitScene {
    private final int SIZE = 500;
    private final Paint BG_COLOUR = Color.AZURE;

    private final Pane layout;


    public HabitScene(Pane layout) {
        this.layout = layout;
    }

    public Scene buildScene() {
        return new Scene(layout, SIZE, SIZE, BG_COLOUR);
    }
}
