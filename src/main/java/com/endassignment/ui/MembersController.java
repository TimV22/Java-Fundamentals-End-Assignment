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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MembersController extends BaseController implements Initializable {

    private final User user;
    private final Database db;
    private final ObservableList<Member> people;
    @FXML
    public Label specifyScreenLabel;
    @FXML
    public TableView tableView;
    @FXML
    public Button collectionButton;
    @FXML
    public Button membersButton;
    @FXML
    public Label errorLabel;
    private Member selectedMember;

    public MembersController(User user, Database db) {
        this.user = user;
        this.db = db;
        people = FXCollections.observableList(db.getPeople());
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        nextScene(actionEvent, "add-edit-members-view.fxml", new AddEditMembersController(user, db));
    }

    @FXML
    public void onEditButtonClick(ActionEvent actionEvent) {
        if (selectedMember != null) {
            nextScene(actionEvent, "add-edit-members-view.fxml", new AddEditMembersController(user, db, selectedMember));
        } else {
            errorLabel.setText("Please select an person to edit");
        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (selectedMember != null) {
            people.remove(selectedMember);
            tableView.setItems(people);
            System.out.println("Person deleted");
        } else {
            errorLabel.setText("Please select an person to delete");
        }
    }

    @FXML
    public void onLendingReceivingButtonClick(MouseEvent mouseEvent) {
        nextScene(mouseEvent, "main-view.fxml", new MainController(user, db));
    }

    @FXML
    public void onMembersButtonClick(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }

    @FXML
    public void onCollectionButtonClick(MouseEvent mouseEvent) {
        nextScene(mouseEvent, "table-view.fxml", new CollectionController(user, db));
    }
    //TODO A search functionality that works on parts of both title and author is not mandatory for a passing grade, but will give points

    private void initTableView() {
        tableView.getColumns().addAll(
                new TableColumn("Identifier") {{
                    setCellValueFactory(new PropertyValueFactory<>("identifier"));
                    prefWidthProperty().bind(tableView.widthProperty().multiply(0.13));

                }},
                new TableColumn("First name") {{
                    setCellValueFactory(new PropertyValueFactory<>("firstName"));
                    prefWidthProperty().bind(tableView.widthProperty().multiply(0.35));

                }},
                new TableColumn("Last name") {{
                    setCellValueFactory(new PropertyValueFactory<>("lastName"));
                    prefWidthProperty().bind(tableView.widthProperty().multiply(0.35));

                }},
                new TableColumn("Birth date") {{
                    setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
                    prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));

                }}
        );
        tableView.setItems(people);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        specifyScreenLabel.setText("Members");
        initTableView();

        //show members as selected tab
        membersButton.setStyle("-fx-background-color: #252525; -fx-min-width: 150; -fx-background-radius: 3 3 0 0;");

        //get selected item
        tableView.getSelectionModel().selectedIndexProperty().addListener((observable -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                selectedMember = (Member) tableView.getSelectionModel().getSelectedItem();
                System.out.println("Selected item: " + selectedMember);
            }
        }));


    }
}
