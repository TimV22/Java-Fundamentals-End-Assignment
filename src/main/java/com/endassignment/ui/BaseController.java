package com.endassignment.ui;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class BaseController {

    protected void nextScene(Event event, String fxml, BaseController controller){
        //get stage and close it
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        event.consume();

        //open main window
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxml));
        fxmlLoader.setController(controller);
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //set theme
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);

        //show main window
        stage.setTitle("Library system");
        stage.setScene(scene);
        stage.show();

    }
}
