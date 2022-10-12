package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Item;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectionController extends BaseController implements Initializable {
    private final User user;
    private final Database db;
    private final ObservableList<Item> items;
    @FXML
    public Button collectionButton;

    public CollectionController(User user, Database db) {
        this.user = user;
        this.db = db;
        items = FXCollections.observableList(db.getItems());
    }

    @FXML
    public Button membersButton;
    @FXML
    public Label specifyScreenLabel;
    @FXML
    public TableView tableView;
    @FXML
    public Label errorLabel;
    private Item selectedItem;

    @FXML
    public void onLendingReceivingButtonClick(MouseEvent mouseEvent) {
        nextScene(mouseEvent, "main-view.fxml", new MainController(user, db));
    }

    @FXML
    public void onCollectionButtonClick(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }

    @FXML
    public void onMembersButtonClick(MouseEvent mouseEvent) {
        nextScene(mouseEvent, "table-view.fxml", new MembersController(user, db));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        specifyScreenLabel.setText("Collection");
        initTableView();

        //show collection as selected tab
        collectionButton.setStyle("-fx-background-color: #252525; -fx-min-width: 150; -fx-background-radius: 3 3 0 0;");

        //get selected item
        tableView.getSelectionModel().selectedIndexProperty().addListener((observable -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                selectedItem = (Item) tableView.getSelectionModel().getSelectedItem();
                System.out.println("Selected item: " + selectedItem);
            }
        }));
    }

    private void initTableView() {
        tableView.getColumns().addAll(
                new TableColumn("Code") {{
                    setCellValueFactory(new PropertyValueFactory<>("code"));
                    setMinWidth(100);
                }},
                new TableColumn("Available") {{
                    setCellValueFactory(new PropertyValueFactory<>("available"));
                    setCellFactory(column -> {
                        return new TableCell<Item, Boolean>() {
                            @Override
                            protected void updateItem(Boolean item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item == null || empty) {
                                    setText(null);
                                } else {
                                    setText(item ? "Yes" : "No");
                                }
                            }
                        };
                    });
                    setMinWidth(100);
                }},
                new TableColumn("Title") {{
                    setCellValueFactory(new PropertyValueFactory<>("title"));
                    setMinWidth(100);
                }},
                new TableColumn("Author") {{
                    setCellValueFactory(new PropertyValueFactory<>("author"));
                    setMinWidth(100);
                }}
        );
        tableView.setItems(items);
    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        nextScene(actionEvent, "add-edit-items-view.fxml", new AddEditItemsController(user, db));
    }

    public void onEditButtonClick(ActionEvent actionEvent) {
        if (selectedItem != null) {
            nextScene(actionEvent, "add-edit-items-view.fxml", new AddEditItemsController(user, db, selectedItem));
        } else {
            errorLabel.setText("Please select an item to edit");
        }
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (selectedItem != null) {
            items.remove(selectedItem);
            tableView.setItems(items);
            System.out.println("Item deleted");
        } else {
            errorLabel.setText("Please select an item to delete");
        }
    }
}
