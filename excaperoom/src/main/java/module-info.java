module com.excape {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires junit;
    requires freetts;

    opens com.excape to javafx.fxml;
    exports com.excape;

    opens com.model to javafx.fxml;
    exports com.model;
}
