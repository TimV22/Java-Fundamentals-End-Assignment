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
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {

    private final User user;
    private final Database db;
    private final ObservableList<Member> people;
    private final ObservableList<Item> items;
    public MainController(User user, Database db) {
        this.user = user;
        this.db = db;
        people = FXCollections.observableList(db.getPeople());
        items = FXCollections.observableList(db.getItems());
    }


    public TextField memberIdentifierField;
    public TextField lendItemCodeField;

    public TextField receiveItemCodeField;
    public Label welcomeLabel;
    public Label lendErrorLabel;
    public Label receiveErrorLabel;

    @FXML
    private void onCollectionButtonClick(MouseEvent actionEvent) {
        nextScene(actionEvent, "collection-view.fxml", new CollectionController(user, db));
    }
    @FXML
    private void onMembersButtonClick(MouseEvent actionEvent) {
        nextScene(actionEvent, "members-view.fxml", new MembersController(user, db));
    }

    public void onLendButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        try {
            lendErrorLabel.setStyle("-fx-text-fill: red");
            if (memberIdentifierField.getText().isEmpty() || lendItemCodeField.getText().isEmpty()) {
                lendErrorLabel.setText("Please fill in all fields");
                return;
            }
            if (people.stream().noneMatch(member -> member.getIdentifier() == (Integer.parseInt(memberIdentifierField.getText())))) {
                lendErrorLabel.setText("Member not found");
                return;
            }
            if (items.stream().noneMatch(item -> item.getCode() == (Integer.parseInt(lendItemCodeField.getText())))) {
                lendErrorLabel.setText("Item not found");
                return;
            }
            if (!items.stream().filter(item -> item.getCode() == (Integer.parseInt(lendItemCodeField.getText()))).findFirst().get().isAvailable()) {
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

    public void onReceiveButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        try {
            receiveErrorLabel.setStyle("-fx-text-fill: red");
            if (receiveItemCodeField.getText().isEmpty()) {
                receiveErrorLabel.setText("Please fill in the item code");
                return;
            }
            if (items.stream().noneMatch(item -> item.getCode() == (Integer.parseInt(receiveItemCodeField.getText())))) {
                receiveErrorLabel.setText("Item not found");
                return;
            }
            if (items.stream().filter(item -> item.getCode() == (Integer.parseInt(receiveItemCodeField.getText()))).findFirst().get().isAvailable()) {
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
