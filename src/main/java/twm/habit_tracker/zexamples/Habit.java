package twm.habit_tracker.zexamples;

import java.util.Objects;

public record Habit(String habitName, Boolean binaryHabit, String habitQuestion, String unit, Double target ) {
    public Habit {
        Objects.requireNonNull(habitName);
        Objects.requireNonNull(binaryHabit);
        Objects.requireNonNull(habitQuestion);
    }
}
