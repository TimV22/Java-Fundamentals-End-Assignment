package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Item;
import com.endassignment.model.Member;
import com.endassignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CollectionController extends BaseController {
    private final User user;
    private final Database db;
    private final ObservableList<Member> people;
    private final ObservableList<Item> items;

    public CollectionController(User user, Database db) {
        this.user = user;
        this.db = db;
        people = FXCollections.observableList(db.getPeople());
        items = FXCollections.observableList(db.getItems());
    }
}
