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
import java.util.ResourceBundle;

public class LendController extends BaseController implements Initializable {

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

    //TODO Lending date

    @FXML
    public void onLendButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();

        try {
            lendErrorLabel.setStyle("-fx-text-fill: red");
            if (memberIdentifierField.getText().isEmpty() || lendItemCodeField.getText().isEmpty()) {
                lendErrorLabel.setText("Please fill in all fields");
                return;
            } else if (people.stream().noneMatch(member -> member.getIdentifier() == (Integer.parseInt(memberIdentifierField.getText())))) {
                lendErrorLabel.setText("Member not found");
                return;
            } else if (items.stream().noneMatch(item -> item.getCode() == (Integer.parseInt(lendItemCodeField.getText())))) {
                lendErrorLabel.setText("Item not found");
                return;
            } else if (!items.stream().filter(item -> item.getCode() == (Integer.parseInt(lendItemCodeField.getText()))).findFirst().get().isAvailable()) {
                lendErrorLabel.setText("Item is already lent");
                return;
            }
        } catch (NumberFormatException e) {
            lendErrorLabel.setText("Please enter a number");
            return;
        }

        Member member = people.stream().filter(m -> m.getIdentifier() == (Integer.parseInt(memberIdentifierField.getText()))).findFirst().get();
        Item item = items.stream().filter(i -> i.getCode() == (Integer.parseInt(lendItemCodeField.getText()))).findFirst().get();
        item.setAvailable(false);
        member.getBorrowedItems().add(item);

        lendErrorLabel.setStyle("-fx-text-fill: green");
        lendErrorLabel.setText("Item lent");

        clearFields();
    }

    @FXML
    public void onReceiveButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        try {
            receiveErrorLabel.setStyle("-fx-text-fill: red");

            if (receiveItemCodeField.getText().isEmpty()) {
                receiveErrorLabel.setText("Please fill in the item code");
                return;
            } else if (items.stream().noneMatch(item -> item.getCode() == (Integer.parseInt(receiveItemCodeField.getText())))) {
                receiveErrorLabel.setText("Item not found");
                return;
            } else if (items.stream().filter(item -> item.getCode() == (Integer.parseInt(receiveItemCodeField.getText()))).findFirst().get().isAvailable()) {
                receiveErrorLabel.setText("Item is not lent");
                return;
            }
        } catch (NumberFormatException e) {
            receiveErrorLabel.setText("Please enter a number");
            return;
        }

        Item item = items.stream().filter(i -> i.getCode() == (Integer.parseInt(receiveItemCodeField.getText()))).findFirst().get();
        item.setAvailable(true);
        people.stream().filter(member -> member.getBorrowedItems().contains(item)).findFirst().get().getBorrowedItems().remove(item);

        receiveErrorLabel.setStyle("-fx-text-fill: green");
        receiveErrorLabel.setText("Item returned");

        clearFields();
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
