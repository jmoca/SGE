module main.sge {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens main.sge to javafx.fxml;
    exports main.sge;
}