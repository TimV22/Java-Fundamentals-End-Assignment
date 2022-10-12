package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Item;
import com.endassignment.model.User;

public class AddEditItemsController extends BaseController {
    public AddEditItemsController(User user, Database db) {
        super();
    }

    public AddEditItemsController(User user, Database db, Item selectedItem) {
        super();
    }
}
