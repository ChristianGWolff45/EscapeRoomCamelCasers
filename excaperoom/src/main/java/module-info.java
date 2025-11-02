package java;

module com.excape {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires freetts;
    requires junit;

    opens com.testing to javafx.fxml;
    exports com.excape;
}
