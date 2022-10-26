package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Member;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditMembersController extends BaseController implements Initializable {

    private final User user;
    private final Database db;
    private final ObservableList<Member> people;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public DatePicker birthDatePicker;
    @FXML
    public Button addEditButton;
    @FXML
    public Label memberLabel;
    private Member selectedMember = null;

    public AddEditMembersController(User user, Database db) {
        super();
        this.user = user;
        this.db = db;
        people = FXCollections.observableList(db.getPeople());
    }

    public AddEditMembersController(User user, Database db, Member selectedMember) {
        this(user, db);
        this.selectedMember = selectedMember;
    }

    @FXML
    public void onLendingReceivingButtonClick(MouseEvent mouseEvent) {
        //to main view
        nextScene(mouseEvent, "main-view.fxml", new MainController(user, db));
    }

    @FXML
    public void onCollectionButtonClick(MouseEvent mouseEvent) {
        //to collection view
        nextScene(mouseEvent, "table-view.fxml", new CollectionController(user, db));
    }

    @FXML
    public void onMembersButtonClick(MouseEvent mouseEvent) {
        //to members view
        nextScene(mouseEvent, "table-view.fxml", new MembersController(user, db));
    }

    @FXML
    public void onMemberButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (checkIfEmpty()) {
            return;
        }

        if (selectedMember == null) {
            //Add new member
            Member member = new Member(people.get(people.size() - 1).getIdentifier() + 1, firstNameField.getText(), lastNameField.getText(), birthDatePicker.getValue());
            people.add(member);
            System.out.println("Added member: " + member);
        } else {
            //Edit member
            selectedMember.setFirstName(firstNameField.getText());
            selectedMember.setLastName(lastNameField.getText());
            selectedMember.setDateOfBirth(birthDatePicker.getValue());
        }
        //back to Members view
        nextScene(actionEvent, "table-view.fxml", new MembersController(user, db));
    }

    @FXML
    public void onCancelButtonClick(ActionEvent actionEvent) {
        //back to Members view
        nextScene(actionEvent, "table-view.fxml", new MembersController(user, db));
    }

    private boolean checkIfEmpty() {
        boolean isEmpty = false;
        //Check if any field is empty
        if (birthDatePicker.getValue() == null) {
            birthDatePicker.setStyle("-fx-border-color: red");
            isEmpty = true;
        } else {
            birthDatePicker.setStyle("-fx-border-color: none");
        }
        if (checkTextFieldEmpty(firstNameField)) {
            isEmpty = true;
        }
        if (checkTextFieldEmpty(lastNameField)) {
            isEmpty = true;
        }
        return isEmpty;
    }

    private boolean checkTextFieldEmpty(TextField textField) {
        if (textField.getText().isEmpty()) {
            textField.setStyle("-fx-border-color: red");
            return true;
        } else {
            textField.setStyle("-fx-border-color: none");
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //if Edit member fill in the fields
        if (selectedMember != null) {
            firstNameField.setText(selectedMember.getFirstName());
            lastNameField.setText(selectedMember.getLastName());
            birthDatePicker.setValue(selectedMember.getDateOfBirth());
            addEditButton.setText("Edit Member");
            memberLabel.setText("Edit Member");
        }
    }
}
