package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.exceptions.UnableToUseFxmlException;
import com.endassignment.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {

    private static final String SELECTED_BACKGROUND_COLOR = "-fx-background-color: #252525; -fx-min-width: 150; -fx-background-radius: 3 3 0 0;";
    private static final String STANDARD_BACKGROUND_COLOR = "-fx-background-color: gray; -fx-min-width: 150; -fx-background-radius: 3 3 0 0;";

    @FXML
    public VBox mainVBox;
    @FXML
    public Button lendReceiveButton;
    @FXML
    public Button collectionButton;
    @FXML
    public Button membersButton;

    public MainController(User user, Database db) {
        super(user, db);
    }

    @FXML
    public void onLendReceiveButtonClick(MouseEvent mouseEvent) {
        //to main view
        loadNextScene("lend-view.fxml", new LendController(user, db));
        mouseEvent.consume();
        colorButtons(lendReceiveButton);
    }

    @FXML
    public void onCollectionButtonClick(MouseEvent mouseEvent) {
        //to collection view
        loadNextScene("collection-table-view.fxml", new CollectionController(user, db, this));
        mouseEvent.consume();
        colorButtons(collectionButton);
    }

    @FXML
    public void onMembersButtonClick(MouseEvent mouseEvent) {
        //to members view
        loadNextScene("member-table-view.fxml", new MembersController(user, db, this));
        mouseEvent.consume();
        colorButtons(membersButton);
    }

    private void colorButtons(Button button) {
        lendReceiveButton.setStyle(STANDARD_BACKGROUND_COLOR);
        collectionButton.setStyle(STANDARD_BACKGROUND_COLOR);
        membersButton.setStyle(STANDARD_BACKGROUND_COLOR);
        button.setStyle(SELECTED_BACKGROUND_COLOR);
    }


    public void loadNextScene(String fxmlFile, BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxmlFile));
        fxmlLoader.setController(controller);

        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
            if (mainVBox.getChildren().size() > 1) {
                mainVBox.getChildren().remove(1);
            }

            //set theme
            JMetro jMetro = new JMetro(Style.DARK);
            jMetro.setScene(scene);

            mainVBox.getChildren().add(scene.getRoot());
        } catch (IOException e) {
            throw new UnableToUseFxmlException(e);
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadNextScene("lend-view.fxml", new LendController(user, db));
        colorButtons(lendReceiveButton);
    }
}
