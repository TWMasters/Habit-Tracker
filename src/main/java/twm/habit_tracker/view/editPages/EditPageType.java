package twm.habit_tracker.view.editPages;

public enum EditPageType {
    HABIT_ADD( true, false, false, "ADD HABIT"),
    HABIT_EDIT(false, true, true, "EDIT HABIT");

    private Boolean addButtonVisible;
    private Boolean saveButtonVisible;
    private Boolean deleteButtonVisible;
    private String pageTitle;

    EditPageType(Boolean addButtonVisible, Boolean saveButtonVisible, Boolean deleteButtonVisible, String pageTitle) {
        this.addButtonVisible = addButtonVisible;
        this.saveButtonVisible = saveButtonVisible;
        this.deleteButtonVisible = deleteButtonVisible;
        this.pageTitle = pageTitle;
    }
}
