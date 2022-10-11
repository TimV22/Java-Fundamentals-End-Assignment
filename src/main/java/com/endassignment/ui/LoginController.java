package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Member;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class LoginController extends BaseController {
    private final Database db = new Database();
    private final ObservableList<Member> people = FXCollections.observableList(db.getPeople());

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorLabel;

    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        for (Member member : people) {
            if (member instanceof User) {
                if (((User) member).getUsername().equals(usernameField.getText()) && ((User) member).getPassword().equals(passwordField.getText())) {
                    System.out.println("Login successful");

                    //get stage and close it
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();

                    nextScene(event, "main-view.fxml", new MainController((User) member, db));

                    //open main window
                    FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("main-view.fxml"));

                    return;
                }
            }
        }
        System.out.println("Login failed");
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());

        //show error message
        errorLabel.setText("Invalid username or password");
    }
}
