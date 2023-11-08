module program.guide {
    requires javafx.controls;
    requires javafx.fxml;


    opens program.guide to javafx.fxml;
    exports program.guide;
}