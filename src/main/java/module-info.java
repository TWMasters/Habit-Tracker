module twm.habit_tracker.habittracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;


    exports twm.habit_tracker.model;

    exports twm.habit_tracker.user_agent;
    opens twm.habit_tracker.user_agent to javafx.fxml;

    exports twm.habit_tracker.view;
    opens twm.habit_tracker.view to javafx.fxml;

    exports twm.habit_tracker.view.mainPages;
    opens twm.habit_tracker.view.mainPages to javafx.fxml;

    exports twm.habit_tracker.view.habitEditPage;
    opens twm.habit_tracker.view.habitEditPage to javafx.fxml;

    exports twm.habit_tracker.zexamples;
    opens twm.habit_tracker.zexamples to javafx.fxml;
    opens twm.habit_tracker.view.editPages to javafx.fxml;
    exports twm.habit_tracker.view.editPages;
}