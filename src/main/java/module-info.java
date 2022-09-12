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

    exports twm.habit_tracker.controller.mainPages;
    opens twm.habit_tracker.controller.mainPages to javafx.fxml;

    exports twm.habit_tracker.controller.editPages;
    opens twm.habit_tracker.controller.editPages to javafx.fxml;

    exports twm.habit_tracker.controller.data;
    opens twm.habit_tracker.controller.data to javafx.fxml;
    exports twm.habit_tracker.model.reward;

}