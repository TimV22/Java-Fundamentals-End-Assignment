module com.tim.endassignment {
    requires org.jfxtras.styles.jmetro;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.endassignment.ui to javafx.fxml;
    opens com.endassignment.model to javafx.base;
    exports com.endassignment.ui;
    exports com.endassignment.model;
    exports com.endassignment.data;
}