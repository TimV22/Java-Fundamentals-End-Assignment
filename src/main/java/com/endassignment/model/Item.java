package com.endassignment.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int Code;
    private String Title;
    private String Author;
    private boolean isAvailable;

    public Item(int Code, String Title, String Author, boolean isAvailable) {
        this.Code = Code;
        this.Title = Title;
        this.Author = Author;
        this.isAvailable = isAvailable;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
