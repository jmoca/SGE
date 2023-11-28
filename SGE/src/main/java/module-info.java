module main.sge {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.sge to javafx.fxml;
    exports main.sge;
}