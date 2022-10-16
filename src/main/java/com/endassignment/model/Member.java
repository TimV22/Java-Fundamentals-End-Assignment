package com.endassignment.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member implements Serializable {
    private int identifier;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private final List<Item> borrowedItems;

    public Member(int identifier, String firstName, String lastName, LocalDate dateOfBirth) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        borrowedItems = new ArrayList<>();
    }

    public Member(int identifier, String firstName, String lastName, LocalDate dateOfBirth, List<Item> borrowedItems) {
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

    public List<Item> getBorrowedItems() {
        return borrowedItems;
    }
}
