package com.endassignment.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class User extends Member implements Serializable {
    private String username;
    private String password;

    public User(int identifier, String username, String firstName, String lastName, String password, LocalDate dateOfBirth) {
        super(identifier, firstName, lastName, dateOfBirth);
        this.username = username;
        this.password = password;
    }

    public User(int identifier, String username, String firstName, String lastName, String password, LocalDate dateOfBirth, List<Item> borrowedItems) {
        super(identifier, firstName, lastName, dateOfBirth, borrowedItems);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
