package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Member;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddEditMembersController extends BaseController {

    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public DatePicker birthDatePicker;
    @FXML
    public Button addEditButton;
    private Member selectedMember = null;
    private User user;
    private Database db;
    private ObservableList<Member> members;

    public AddEditMembersController(User user, Database db) {
        super();
        this.user = user;
        this.db = db;
        members = FXCollections.observableList(db.getPeople());
    }

    //TODO MAKE EDIT VERSION
    //TODO CHECK IF NEW MEMBER GETS SAVED IN DATABASE
    public AddEditMembersController(User user, Database db, Member selectedMember) {
        this(user, db);
        this.selectedMember = selectedMember;

    }

    @FXML
    public void onLendingReceivingButtonClick(MouseEvent mouseEvent) {
        nextScene(mouseEvent, "main-view.fxml", new MainController(user, db));
    }

    @FXML
    public void onCollectionButtonClick(MouseEvent mouseEvent) {
        nextScene(mouseEvent, "table-view.fxml", new CollectionController(user, db));
    }

    @FXML
    public void onMembersButtonClick(MouseEvent mouseEvent) {
        nextScene(mouseEvent, "table-view.fxml", new MembersController(user, db));
    }

    @FXML
    public void onMemberButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (checkIfEmpty()) {
            return;
        }

        if (selectedMember == null) {
            Member member = new Member(members.get(members.size() - 1).getIdentifier() + 1, firstNameField.getText(), lastNameField.getText(), birthDatePicker.getValue());
            members.add(member);
        } else {
            selectedMember.setFirstName(firstNameField.getText());
            selectedMember.setLastName(lastNameField.getText());
            selectedMember.setDateOfBirth(birthDatePicker.getValue());
        }
        nextScene(actionEvent, "table-view.fxml", new MembersController(user, db));
    }

    @FXML
    public void onCancelButtonClick(ActionEvent actionEvent) {
        nextScene(actionEvent, "table-view.fxml", new MembersController(user, db));
    }

    private boolean checkIfEmpty() {
        boolean isEmpty = false;
        if (birthDatePicker.getValue() == null) {
            birthDatePicker.setStyle("-fx-border-color: red");
            isEmpty = true;
        } else {
            birthDatePicker.setStyle("-fx-border-color: none");
        }
        if (firstNameField.getText().isEmpty()) {
            firstNameField.setStyle("-fx-border-color: red");
            isEmpty = true;
        } else {
            firstNameField.setStyle("-fx-border-color: none");
        }
        if (lastNameField.getText().isEmpty()) {
            lastNameField.setStyle("-fx-border-color: red");
            isEmpty = true;
        } else {
            lastNameField.setStyle("-fx-border-color: none");
        }
        return isEmpty;
    }
}
