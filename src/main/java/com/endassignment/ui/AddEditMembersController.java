package com.endassignment.ui;

import com.endassignment.data.Database;
import com.endassignment.model.Member;
import com.endassignment.model.User;

public class AddEditMembersController extends BaseController {
    public AddEditMembersController(User user, Database db) {
        super();
    }

    public AddEditMembersController(User user, Database db, Member selectedMember) {
        super();
    }
}
