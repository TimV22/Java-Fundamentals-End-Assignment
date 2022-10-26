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

@SuppressWarnings("rawtypes")
public class CollectionController extends BaseController implements Initializable {
    private final ObservableList<Item> items;
    @FXML
    public Button collectionButton;
    @FXML
    public Button membersButton;
    @FXML
    public Label specifyScreenLabel;
    @FXML
    public TableView tableView;
    @FXML
    public Label errorLabel;
    private Item selectedItem;

    public CollectionController(User user, Database db) {
        super(user, db);
        items = FXCollections.observableList(db.getItems());
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
                    prefWidthProperty().bind(tableView.widthProperty().multiply(0.13));
                }},
                new TableColumn("Available") {{
                    setCellValueFactory(new PropertyValueFactory<>("available"));
                    setCellFactory(column ->
                            new TableCell<Item, Boolean>() {
                                @Override
                                protected void updateItem(Boolean item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item == null || empty) {
                                        setText(null);
                                    } else {
                                        setText(Boolean.TRUE.equals(item) ? "Yes" : "No");
                                    }
                                }
                            });
                    prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
                }},
                new TableColumn("Title") {{
                    setCellValueFactory(new PropertyValueFactory<>("title"));
                    prefWidthProperty().bind(tableView.widthProperty().multiply(0.35));

                }},
                new TableColumn("Author") {{
                    setCellValueFactory(new PropertyValueFactory<>("author"));
                    prefWidthProperty().bind(tableView.widthProperty().multiply(0.35));

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
