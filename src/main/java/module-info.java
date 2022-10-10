module com.tim.endassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.endassignment.ui to javafx.fxml;
    exports com.endassignment.ui;
}