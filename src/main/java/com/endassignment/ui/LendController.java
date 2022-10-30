package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Item;
import com.endassignment.model.Member;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class LendController extends BaseController implements Initializable {

    private static final String ERROR_LABEL_STYLE = "-fx-text-fill: red";
    private final ObservableList<Member> people;
    private final ObservableList<Item> items;
    @FXML
    public TextField memberIdentifierField;
    @FXML
    public TextField lendItemCodeField;
    @FXML
    public TextField receiveItemCodeField;
    @FXML
    public Label welcomeLabel;
    @FXML
    public Label lendErrorLabel;
    @FXML
    public Label receiveErrorLabel;

    public LendController(User user, Database db) {
        super(user, db);
        people = FXCollections.observableList(db.getPeople());
        items = FXCollections.observableList(db.getItems());
    }

    @FXML
    public void onLendButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        try {
            if (!allLendFieldCorrect())
                return;
        } catch (NumberFormatException e) {
            lendErrorLabel.setText("Please enter a number");
            return;
        }
        if (people.isEmpty() || items.isEmpty()) {
            lendErrorLabel.setText("No members or items in database");
            return;
        }
        Member member = people.stream().filter(m ->
                m.getIdentifier() == (Integer.parseInt(memberIdentifierField.getText()))).findFirst().get();
        Item item = items.stream().filter(i ->
                i.getCode() == (Integer.parseInt(lendItemCodeField.getText()))).findFirst().get();
        item.setAvailable(false);
        member.getBorrowedItems().put(item, LocalDate.now());

        lendErrorLabel.setStyle("-fx-text-fill: green");
        lendErrorLabel.setText("Item lent");

        clearFields();
    }

    private boolean allLendFieldCorrect() {
        lendErrorLabel.setStyle(ERROR_LABEL_STYLE);

        if (memberIdentifierField.getText().isEmpty() || lendItemCodeField.getText().isEmpty()) {
            lendErrorLabel.setText("Please fill in all fields");
            return false;
        } else if (people.stream().noneMatch(member -> member.getIdentifier() == (Integer.parseInt(memberIdentifierField.getText())))) {
            lendErrorLabel.setText("Member not found");
            return false;
        } else if (items.stream().noneMatch(item -> item.getCode() == (Integer.parseInt(lendItemCodeField.getText())))) {
            lendErrorLabel.setText("Item not found");
            return false;
        } else if (!items.stream().filter(item ->
                item.getCode() == (Integer.parseInt(lendItemCodeField.getText()))).findFirst().get().isAvailable()) {
            lendErrorLabel.setText("Item is already lent");
            return false;
        }
        return true;
    }

    @FXML
    public void onReceiveButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        try {
            if (!allReceiveFieldCorrect())
                return;
        } catch (NumberFormatException e) {
            receiveErrorLabel.setText("Please enter a number");
            return;
        }

        Item item = items.stream().filter(i ->
                i.getCode() == (Integer.parseInt(receiveItemCodeField.getText()))).findFirst().get();
        item.setAvailable(true);
        Map<Item, LocalDate> borrowedItems = people.stream().filter(member ->
                member.getBorrowedItems().get(item) != null).findFirst().get().getBorrowedItems();
        LocalDate borrowedDate = borrowedItems.get(item);
        if (borrowedDate.plusDays(21).isBefore(LocalDate.now())) {
            int daysLate = (int) (LocalDate.now().toEpochDay() - borrowedDate.plusDays(21).toEpochDay());
            receiveErrorLabel.setText("Item is " + daysLate + " days overdue");
            receiveErrorLabel.setStyle(ERROR_LABEL_STYLE);
        } else {
            receiveErrorLabel.setText("Item received");
            receiveErrorLabel.setStyle("-fx-text-fill: green");
        }

        clearFields();
    }

    private boolean allReceiveFieldCorrect() {
        receiveErrorLabel.setStyle(ERROR_LABEL_STYLE);

        if (receiveItemCodeField.getText().isEmpty()) {
            receiveErrorLabel.setText("Please fill in the item code");
            return false;
        } else if (items.stream().noneMatch(item -> item.getCode() == (Integer.parseInt(receiveItemCodeField.getText())))) {
            receiveErrorLabel.setText("Item not found");
            return false;
        } else if (items.stream().filter(item ->
                item.getCode() == (Integer.parseInt(receiveItemCodeField.getText()))).findFirst().get().isAvailable()) {
            receiveErrorLabel.setText("Item is not lent");
            return false;
        }
        return true;
    }

    private void clearFields() {
        memberIdentifierField.clear();
        lendItemCodeField.clear();
        receiveItemCodeField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Welcome, " + user.getFirstName() + " " + user.getLastName());
    }
}
