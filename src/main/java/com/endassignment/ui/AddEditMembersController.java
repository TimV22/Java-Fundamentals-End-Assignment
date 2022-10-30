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
import javafx.util.converter.LocalDateStringConverter;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddEditMembersController extends BaseController implements Initializable {

    private final ObservableList<Member> people;
    private final MainController mainController;
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

    public AddEditMembersController(User user, Database db, MainController mainController) {
        super(user, db);
        people = FXCollections.observableList(db.getPeople());
        this.mainController = mainController;
    }

    public AddEditMembersController(User user, Database db, Member selectedMember, MainController mainController) {
        this(user, db, mainController);
        this.selectedMember = selectedMember;
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
        mainController.loadNextScene("member-table-view.fxml", new MembersController(user, db, mainController));
    }

    @FXML
    public void onCancelButtonClick(ActionEvent actionEvent) {
        //back to Members view
        mainController.loadNextScene("member-table-view.fxml", new MembersController(user, db, mainController));
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
        //made by using https://examples.javacodegeeks.com/desktop-java/javafx/datepicker-javafx/javafx-datepicker-example/#:~:text=By%20default%2C%20it%20formats%20dates,MM%2Fdd%2Fyyyy%20format.

        // Make Date Time Converter
        String pattern = "dd-MM-yyyy";
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(pattern);

        // Set the Date Time Converter
        birthDatePicker.setConverter(new LocalDateStringConverter(dtFormatter, null));

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
