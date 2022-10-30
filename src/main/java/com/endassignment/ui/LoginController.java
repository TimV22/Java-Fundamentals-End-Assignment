package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Member;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class LoginController extends BaseController {
    private final ObservableList<Member> people;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorLabel;
    public LoginController() {
        super(null, new Database());
        people = FXCollections.observableList(db.getPeople());
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) {
        for (Member member : people) {
            if (member instanceof User user && user.getUsername().equals(usernameField.getText()) && user.getPassword().equals(passwordField.getText())) {
                System.out.println("Login successful");

                //get stage and close it
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                loadMainView(event, new MainController(user, db));
                return;
            }
        }
        System.out.println("Login failed");
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());

        //show error message
        errorLabel.setText("Invalid username or password");
    }

    private void closeWindowEvent(WindowEvent windowEvent) {
        System.out.println("Closing window");
        db.save();
    }

    private void loadMainView(Event event, BaseController controller) {
        Stage stage = new Stage();
        event.consume();

        Scene scene = getScene("main-view.fxml", controller);

        //set theme
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);

        stage.setResizable(false);
        stage.setHeight(500);

        //adding event to save the database when program is closed
        showNewScene(stage, scene);
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

}

