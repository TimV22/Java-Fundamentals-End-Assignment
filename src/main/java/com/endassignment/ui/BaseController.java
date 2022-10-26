package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class BaseController {

    protected final User user;
    protected final Database db;

    public BaseController(){
        this.user = null;
        this.db = null;
    }

    public BaseController(User user, Database db) {
        this.user = user;
        this.db = db;
    }

    protected void nextScene(Event event, String fxml, BaseController controller) {
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
    @FXML
    public void onLendingReceivingButtonClick(MouseEvent mouseEvent) {
        //to main view
        nextScene(mouseEvent, "main-view.fxml", new MainController(user, db));
        mouseEvent.consume();
    }

    @FXML
    public void onCollectionButtonClick(MouseEvent mouseEvent) {
        //to collection view
        nextScene(mouseEvent, "table-view.fxml", new CollectionController(user, db));
        mouseEvent.consume();
    }

    @FXML
    public void onMembersButtonClick(MouseEvent mouseEvent) {
        //to members view
        nextScene(mouseEvent, "table-view.fxml", new MembersController(user, db));
        mouseEvent.consume();
    }
}
