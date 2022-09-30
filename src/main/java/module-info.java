module com.tim.endassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tim.endassignment to javafx.fxml;
    exports com.tim.endassignment;
}