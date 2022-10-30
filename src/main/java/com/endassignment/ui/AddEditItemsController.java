package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Item;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AddEditItemsController extends BaseController implements Initializable {

    private final ObservableList<Item> items;
    private final MainController mainController;
    @FXML
    public TextField titleField;
    @FXML
    public TextField authorField;
    @FXML
    public Button addEditButton;
    @FXML
    public Label itemLabel;
    private Item selectedItem = null;

    public AddEditItemsController(User user, Database db, MainController mainController) {
        super(user, db);
        items = FXCollections.observableList(db.getItems());
        this.mainController = mainController;
    }

    public AddEditItemsController(User user, Database db, Item selectedItem, MainController mainController) {
        this(user, db, mainController);
        this.selectedItem = selectedItem;
    }


    @FXML
    public void onAddEditItemButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (checkIfEmpty()) {
            return;
        }

        if (selectedItem == null) {
            //Add new item
            Item item = new Item(items.sorted(Comparator.comparingInt(Item::getCode)).get(items.size() - 1).getCode() + 1, titleField.getText(), authorField.getText(), true);
            items.add(item);
        } else {
            //edit item
            selectedItem.setTitle(titleField.getText());
            selectedItem.setAuthor(authorField.getText());
        }
        //back to collection
        mainController.loadNextScene("collection-table-view.fxml", new CollectionController(user, db, mainController));
    }

    @FXML
    public void onCancelButtonClick(ActionEvent actionEvent) {
        //back to collection
        mainController.loadNextScene("collection-table-view.fxml", new CollectionController(user, db, mainController));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //if edit item fill fields
        if (selectedItem != null) {
            titleField.setText(selectedItem.getTitle());
            authorField.setText(selectedItem.getAuthor());
            addEditButton.setText("Edit item");
            itemLabel.setText("Edit Item");
        }
    }

    private boolean checkIfEmpty() {
        boolean isEmpty = checkTextFieldEmpty(authorField);
        if (checkTextFieldEmpty(titleField) && !isEmpty) {
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
}
