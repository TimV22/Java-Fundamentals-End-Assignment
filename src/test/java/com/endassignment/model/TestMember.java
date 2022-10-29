package com.endassignment.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMember {

    @Test
    void testIdentifier() {
        Member member = new Member(1, "username", "first name", LocalDate.now());

        member.setIdentifier(2);

        assertEquals(2, member.getIdentifier());
    }

    @Test
    void testFirstName() {
        Member member = new Member(1, "Username", "FirstName", LocalDate.now());

        member.setFirstName("new first name");

        assertEquals("new first name", member.getFirstName());
    }

    @Test
    void testLastName() {
        Member member = new Member(1, "FirstName", "LastName", LocalDate.now());

        member.setLastName("new lastname");

        assertEquals("new lastname", member.getLastName());
    }

    @Test
    void testDateOfBirth() {
        Member member = new Member(1, "FirstName", "LastName", LocalDate.of(2000, 1, 1));

        member.setDateOfBirth(LocalDate.of(2001, 1, 1));

        assertEquals(LocalDate.of(2001, 1, 1), member.getDateOfBirth());
    }

    @Test
    void testBorrowedItems() {
        HashMap<Item, LocalDate> items = new HashMap<>();
        items.put(new Item(1, "1", "1", true), LocalDate.of(2001, 1, 1));
        Item item = new Item(2, "2", "2", true);
        Member member = new Member(1, "FirstName", "LastName", LocalDate.of(2000, 1, 1), items);
        items.put(item, LocalDate.of(2001, 1, 1));

        Map<Item, LocalDate> hashMap = member.getBorrowedItems();
        hashMap.put(item, LocalDate.of(2001, 1, 1));


        assertEquals(items, member.getBorrowedItems());
    }

    @Test
    void testMember() {
        Member member = new Member(1, "FirstName", "LastName", LocalDate.of(2000, 1, 1));


        assertEquals(1, member.getIdentifier());
        assertEquals("FirstName", member.getFirstName());
        assertEquals("LastName", member.getLastName());
        assertEquals(LocalDate.of(2000, 1, 1), member.getDateOfBirth());
    }
}
