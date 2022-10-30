package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.exceptions.UnableToUseFxmlException;
import com.endassignment.model.User;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class BaseController {

    protected final User user;
    protected final Database db;


    public BaseController() {
        this.user = null;
        this.db = null;
    }

    public BaseController(User user, Database db) {
        this.user = user;
        this.db = db;
    }




    protected Stage getStage(Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    protected Scene getScene(String fxml, BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxml));
        fxmlLoader.setController(controller);
        try {
            return new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new UnableToUseFxmlException(e);
        }

    }

    protected void showNewScene(Stage stage, Scene scene) {
        //show main window
        stage.setTitle("Library system");
        stage.setScene(scene);
        stage.show();
    }

}
