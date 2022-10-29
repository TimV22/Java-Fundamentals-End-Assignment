package com.endassignment.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

public class Member implements Serializable {
    private final HashMap<Item, LocalDate> borrowedItems;
    private int identifier;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public Member(int identifier, String firstName, String lastName, LocalDate dateOfBirth) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        borrowedItems = new HashMap<>();
    }

    public Member(int identifier, String firstName, String lastName, LocalDate dateOfBirth, HashMap<Item, LocalDate> borrowedItems) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.borrowedItems = borrowedItems;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HashMap<Item, LocalDate> getBorrowedItems() {
        return borrowedItems;
    }
}
