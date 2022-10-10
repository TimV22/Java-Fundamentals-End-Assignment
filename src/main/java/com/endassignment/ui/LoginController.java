package com.endassignment.ui;

import com.endassignment.data.Database;
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

public class LoginController {
    private final Database db = new Database();
    private final ObservableList<User> users = FXCollections.observableList(db.getUsers());

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    Label errorLabel;

    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        for (User user : users) {
            if (user.getUsername().equals(usernameField.getText()) && user.getPassword().equals(passwordField.getText())) {
                System.out.println("Login successful");
                //close login window
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                //open main window
                FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("main-view.fxml"));
                fxmlLoader.setController(new MainController());
                Scene scene = new Scene(fxmlLoader.load());

                JMetro jMetro = new JMetro(Style.DARK);
                jMetro.setScene(scene);

                stage.setTitle("Library system");
                stage.setScene(scene);
                stage.show();

                return;
            }
        }
        System.out.println("Login failed");
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());
        errorLabel.setText("Invalid username or password");
    }
}
