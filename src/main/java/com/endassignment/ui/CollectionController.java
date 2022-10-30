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

import java.net.URL;
import java.util.ResourceBundle;


public class CollectionController extends BaseController implements Initializable {
    private final ObservableList<Item> items;
    private final MainController mainController;
    @FXML
    public Button collectionButton;
    @FXML
    public Button membersButton;
    @FXML
    public TableColumn<Item, Boolean> availableTableColumn;
    @FXML
    public TextField searchField;
    @FXML
    public TableView<Item> collectionTableView;
    @FXML
    public Label errorLabel;
    private Item selectedItem;

    public CollectionController(User user, Database db, MainController mainController) {
        super(user, db);
        items = FXCollections.observableList(db.getItems());
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableView();

        //get selected item
        collectionTableView.getSelectionModel().selectedIndexProperty().addListener((observable -> {
            if (collectionTableView.getSelectionModel().getSelectedItem() != null) {
                selectedItem = collectionTableView.getSelectionModel().getSelectedItem();
                System.out.println("Selected item: " + selectedItem);
            }
        }));

        //search functionality for collection
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                collectionTableView.setItems(items);
            } else {
                ObservableList<Item> filteredItems = FXCollections.observableArrayList();
                items.forEach(item -> {
                    if (item.getTitle().toLowerCase().contains(newValue.toLowerCase())
                            || item.getAuthor().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredItems.add(item);
                    }
                });
                collectionTableView.setItems(filteredItems);
            }
        });
    }

    private void initTableView() {
        if (availableTableColumn != null) {

            //used https://stackoverflow.com/questions/40090540/javafx-display-text-instead-of-boolean-value-true-false for help
            //convert the boolean value to a string "yes" or "no"
            availableTableColumn.setCellFactory(column -> new TableCell<Item, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(Boolean.TRUE.equals(item) ? "Yes" : "No");
                    }
                }
            });
        }

        collectionTableView.setItems(items);
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        //load add item view
        mainController.loadNextScene("add-edit-items-view.fxml", new AddEditItemsController(user, db, mainController));
    }

    @FXML
    public void onEditButtonClick(ActionEvent actionEvent) {
        //load edit item view or give an error when no item is selected
        if (selectedItem != null) {
            mainController.loadNextScene("add-edit-items-view.fxml", new AddEditItemsController(user, db, selectedItem, mainController));
        } else {
            errorLabel.setText("Please select an item to edit");
        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        //delete item or give an error when no item is selected
        actionEvent.consume();
        if (selectedItem != null) {
            items.remove(selectedItem);
            collectionTableView.setItems(items);
            System.out.println("Item deleted");
        } else {
            errorLabel.setText("Please select an item to delete");
        }
    }
}
