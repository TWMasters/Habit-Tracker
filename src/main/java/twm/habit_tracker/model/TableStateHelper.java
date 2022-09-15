package twm.habit_tracker.model;

class TableStateHelper {
    private static final String NULL_STRING = "null";

    /**
     * Helper method for converting non-null Unit Values into correct format
     * @param input Raw Unit Value
     * @return Formatted Unit Value
     */
    static String editUnitIfNotNull(String input) {
        String output = input.equals(NULL_STRING) ? input : "\'" + input + "\'";
        return output;
    }
}
