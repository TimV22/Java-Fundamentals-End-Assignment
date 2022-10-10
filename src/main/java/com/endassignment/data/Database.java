package com.endassignment.data;

import com.endassignment.model.Item;
import com.endassignment.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final File userFile = new File("src/main/resources/com/endassignment/ui/users.bat");
    private static final File itemFile = new File("src/main/resources/com/endassignment/ui/items.bat");
    private List<User> users = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public static void main(String[] args) {
        Database db = new Database();
    }

    public Database() {
        if (!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            users.add(new User("Ilene", "Ilene", "Skinner", "Ilene123", LocalDate.of(1958, 4, 13)));
            users.add(new User("Terell", "Terell", "Park", "Terell123", LocalDate.of(1959, 10, 16)));
            users.add(new User("Lavonne", "Lavonne", "Henderson", "Lavonne123", LocalDate.of(1960, 5, 3)));
            users.add(new User("Riordan", "Riordan", "Lane", "Riordan123", LocalDate.of(1979, 10, 21)));
            users.add(new User("Terry", "Terry", "Brasher", "Terry123", LocalDate.of(1982, 7, 31)));
            users.add(new User("Charla", "Charla", "Upton", "Charla123", LocalDate.of(2001, 12, 7)));
            save(users, userFile);
        }
        if (!itemFile.exists()) {
            try {
                itemFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            items.add(new Item(1, "Absalom, Absalom!", "William Faulkner", true));
            items.add(new Item(2, "A time to kill", "John Grisham", true));
            items.add(new Item(3, "The house of mirth", "Edith Wharton", true));
            items.add(new Item(4, "Item 4 description", 4.99, 4));
            items.add(new Item(5, "Item 5 description", 5.99, 5));
            items.add(new Item(6, "Item 6 description", 6.99, 6));
            save(items, itemFile);
        }

    }


    private void save(List list, File file) {
        //save list to file
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Object object : list) {
                oos.writeObject(object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void load(){
        //load list from file

    }

    public boolean fileExists(File file){
        return file.exists();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Item> getItems() {
        return items;
    }
}
