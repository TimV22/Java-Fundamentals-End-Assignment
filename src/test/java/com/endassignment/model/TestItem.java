package com.endassignment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestItem {

    @Test
    void testAvailable() {
        Item item = new Item(1, "title", "author", true);

        item.setAvailable(false);

        assertFalse(item.isAvailable());
    }

    @Test
    void testAuthor() {
        Item item = new Item(1, "title", "author", true);

        item.setAuthor("new author");

        assertEquals("new author", item.getAuthor());
    }

    @Test
    void testCode() {
        Item item = new Item(1, "title", "author", true);

        item.setCode(2);

        assertEquals(2, item.getCode());
    }

    @Test
    void testTitle() {
        Item item = new Item(1, "title", "author", true);

        item.setTitle("new title");

        assertEquals("new title", item.getTitle());
    }

    @Test
    void testItem() {
        Item item = new Item(1, "title", "author", true);

        assertEquals(1, item.getCode());
        assertEquals("title", item.getTitle());
        assertEquals("author", item.getAuthor());
        assertTrue(item.isAvailable());
    }

}
