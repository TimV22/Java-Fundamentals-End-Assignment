package com.endassignment.data;

import com.endassignment.model.Item;
import com.endassignment.model.User;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final File dataFile = new File("src/main/resources/com/endassignment/ui/data.dat");
    private List<User> users = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public Database() {
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            users.add(new User("Ilene", "Ilene", "Skinner", "Ilene123", LocalDate.of(1958, 4, 13)));
            users.add(new User("Terrell", "Terrell", "Park", "Terrell123", LocalDate.of(1959, 10, 16)));
            users.add(new User("Lavonne", "Lavonne", "Henderson", "Lavonne123", LocalDate.of(1960, 5, 3)));
            users.add(new User("Riordan", "Riordan", "Lane", "Riordan123", LocalDate.of(1979, 10, 21)));
            users.add(new User("Terry", "Terry", "Brasher", "Terry123", LocalDate.of(1982, 7, 31)));
            users.add(new User("Charla", "Charla", "Upton", "Charla123", LocalDate.of(2001, 12, 7)));

            items.add(new Item(1, "Absalom, Absalom!", "William Faulkner", true));
            items.add(new Item(2, "A time to kill", "John Grisham", true));
            items.add(new Item(3, "The house of mirth", "Edith Wharton", true));
            items.add(new Item(4, "East of eden", "John SteinBeck", true));
            items.add(new Item(5, "The sun also rises", "Ernest Hemingway", true));
            items.add(new Item(6, "Vile bodies", "Evelyn Waugh", true));

            save();
        } else{
            load();
        }
    }


    private void save() {
        //save list to file
        try (FileOutputStream fos = new FileOutputStream(dataFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Object object : users) {
                oos.writeObject(object);
            }
            for (Object object : items) {
                oos.writeObject(object);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void load() {
        //load list from file
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(dataFile))) {
            while (true) {
                try {
                    Object object = ois.readObject();
                    if (object instanceof User) {
                        users.add((User) object);
                    } else if (object instanceof Item) {
                        items.add((Item) object);
                    }
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Item> getItems() {
        return items;
    }
}
