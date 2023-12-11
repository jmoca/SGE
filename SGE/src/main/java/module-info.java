module main.sge {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mariadb.jdbc;


    opens main.sge to javafx.fxml;
    exports main.sge;
}