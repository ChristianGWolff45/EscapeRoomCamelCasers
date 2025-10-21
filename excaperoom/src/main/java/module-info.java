module com.excape {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires freetts;

    opens com.excape to javafx.fxml;
    exports com.excape;
}
