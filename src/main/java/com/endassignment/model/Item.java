package com.endassignment.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int code;
    private String title;
    private String author;
    private boolean isAvailable;

    public Item(int code, String title, String author, boolean isAvailable) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
