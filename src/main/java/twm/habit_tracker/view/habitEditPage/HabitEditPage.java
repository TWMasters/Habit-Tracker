package twm.habit_tracker.view.habitEditPage;

import java.util.function.Consumer;

public class HabitEditPage {
    private static Consumer<String[]> addHabitConsumer;

    public static void setAddHabitConsumer(Consumer<String[]> addHabitConsumer) {
        HabitEditPage.addHabitConsumer = addHabitConsumer;
    }
}
