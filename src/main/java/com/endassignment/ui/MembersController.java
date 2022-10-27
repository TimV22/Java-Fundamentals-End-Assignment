package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Member;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MembersController extends BaseController implements Initializable {

    private final ObservableList<Member> people;
    private final MainController mainController;
    @FXML
    public TableView<Member> memberTableView;
    @FXML
    public Label errorLabel;
    private Member selectedMember;

    public MembersController(User user, Database db, MainController mainController) {
        super(user, db);
        people = FXCollections.observableList(db.getPeople());
        this.mainController = mainController;
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        mainController.loadNextScene("add-edit-members-view.fxml", new AddEditMembersController(user, db, mainController));
    }

    @FXML
    public void onEditButtonClick(ActionEvent actionEvent) {
        if (selectedMember != null) {
            mainController.loadNextScene("add-edit-members-view.fxml", new AddEditMembersController(user, db, selectedMember, mainController));
        } else {
            errorLabel.setText("Please select an person to edit");
        }
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (selectedMember != null) {
            people.remove(selectedMember);
            memberTableView.setItems(people);
            System.out.println("Person deleted");
        } else {
            errorLabel.setText("Please select an person to delete");
        }
    }
    //TODO A search functionality that works on parts of both title and author is not mandatory for a passing grade, but will give points

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberTableView.setItems(people);

        //get selected item
        memberTableView.getSelectionModel().selectedIndexProperty().addListener((observable -> {
            if (memberTableView.getSelectionModel().getSelectedItem() != null) {
                selectedMember = memberTableView.getSelectionModel().getSelectedItem();
                System.out.println("Selected item: " + selectedMember);
            }
        }));


    }
}