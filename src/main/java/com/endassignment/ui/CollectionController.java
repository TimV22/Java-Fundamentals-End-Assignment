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
    }

    private void initTableView() {
        if (availableTableColumn != null) {

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
        mainController.loadNextScene("add-edit-items-view.fxml", new AddEditItemsController(user, db, mainController));
    }

    @FXML
    public void onEditButtonClick(ActionEvent actionEvent) {
        if (selectedItem != null) {
            mainController.loadNextScene("add-edit-items-view.fxml", new AddEditItemsController(user, db, selectedItem, mainController));
        } else {
            errorLabel.setText("Please select an item to edit");
        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
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
