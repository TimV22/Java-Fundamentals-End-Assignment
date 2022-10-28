package com.endassignment.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

    @Test
    void testIdentifier() {
        User user = new User(1, "username", "first name", "last name", "password", LocalDate.now());

        user.setIdentifier(2);

        assertEquals(2, user.getIdentifier());
    }

    @Test
    void testUsername() {
        User user = new User(1, "Username", "FirstName", "LastName", "Password", LocalDate.now());

        user.setUsername("new username");

        assertEquals("new username", user.getUsername());
    }

    @Test
    void testFirstName() {
        User user = new User(1, "Username", "FirstName", "LastName", "Password", LocalDate.now());

        user.setFirstName("new first name");

        assertEquals("new first name", user.getFirstName());
    }

    @Test
    void testLastName() {
        User user = new User(1, "Username", "FirstName", "LastName", "Password", LocalDate.now());

        user.setLastName("new last name");

        assertEquals("new last name", user.getLastName());
    }

    @Test
    void testPassword() {
        User user = new User(1, "Username", "FirstName", "LastName", "Password", LocalDate.now());

        user.setPassword("new password");

        assertEquals("new password", user.getPassword());
    }

    @Test
    void testDateOfBirth() {
        User user = new User(1, "Username", "FirstName", "LastName", "Password", LocalDate.of(2000, 1, 1));

        user.setDateOfBirth(LocalDate.of(2001, 1, 1));

        assertEquals(LocalDate.of(2001, 1, 1), user.getDateOfBirth());
    }

    @Test
    void testUser() {
        User user = new User(1, "Username", "FirstName", "LastName", "Password", LocalDate.of(2000, 1, 1));

        assertEquals(1, user.getIdentifier());
        assertEquals("Username", user.getUsername());
        assertEquals("FirstName", user.getFirstName());
        assertEquals("LastName", user.getLastName());
        assertEquals("Password", user.getPassword());
        assertEquals(LocalDate.of(2000, 1, 1), user.getDateOfBirth());
    }
}
