module com.tim.endassignment {
    requires org.jfxtras.styles.jmetro;
    requires javafx.controls;
    requires javafx.fxml;



    opens com.endassignment.ui to javafx.fxml;
    exports com.endassignment.ui;
}