module program.guide {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens program.guide to javafx.fxml;
    exports program.guide;
}