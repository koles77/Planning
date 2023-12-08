module com.example.planning {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.example.planning to javafx.fxml;
    exports com.example.planning;
    exports com.example.planning.controllers;
    opens com.example.planning.controllers to javafx.fxml;
}