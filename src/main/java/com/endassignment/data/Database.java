package com.endassignment.data;

import com.endassignment.model.Item;
import com.endassignment.model.Member;
import com.endassignment.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final File dataFile = new File("src/main/resources/com/endassignment/ui/data.dat");
    private final List<Member> people = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public Database() {
        if (!dataFile.exists()) {
            loadStandardData();
            System.out.println("Loaded standard data");
        } else{
            System.out.println("Loaded data from file");
            load();
        }
    }

    private void loadStandardData(){
        try {
            dataFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        people.add(new User(1,"Ilene", "Ilene", "Skinner", "Ilene123", LocalDate.of(1958, 4, 13)));
        people.add(new User(2, "Terell", "Terell", "Park", "Terell123", LocalDate.of(1959, 10, 16)));
        people.add(new User(3, "Lavonne", "Lavonne", "Henderson", "Lavonne123", LocalDate.of(1960, 5, 3)));
        people.add(new Member(5, "Eustace", "Faulkner", LocalDate.of(1960, 5, 3)));
        people.add(new Member(6, "Malakai", "Haig", LocalDate.of(1971, 1, 27)));
        people.add(new Member(7,"Odetta", "Sempers", LocalDate.of(1979, 3, 23)));
        people.add(new User(8, "Riordan", "Riordan", "Lane", "Riordan123", LocalDate.of(1979, 10, 21)));
        people.add(new User(9, "Terry", "Terry", "Brasher", "Terry123", LocalDate.of(1982, 7, 31)));
        people.add(new User(10, "Charla", "Charla", "Upton", "Charla123", LocalDate.of(2001, 12, 7)));
        people.add(new Member(11, "Lorrie", "Barnes", LocalDate.of(1982, 7, 31)));
        people.add(new Member(12, "Lulu", "Law", LocalDate.of(1966, 3, 20)));
        people.add(new Member(13, "Ellie", "Leonard", LocalDate.of(1999, 3, 13)));


        items.add(new Item(1, "Absalom, Absalom!", "William Faulkner", true));
        items.add(new Item(2, "A time to kill", "John Grisham", true));
        items.add(new Item(3, "The house of mirth", "Edith Wharton", true));
        items.add(new Item(4, "East of eden", "John SteinBeck", true));
        items.add(new Item(5, "The sun also rises", "Ernest Hemingway", true));
        items.add(new Item(6, "Vile bodies", "Evelyn Waugh", true));

        save();
    }


    public void save() {
        //save list to file
        try (FileOutputStream fos = new FileOutputStream(dataFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Object object : people) {
                oos.writeObject(object);
            }
            for (Object object : items) {
                oos.writeObject(object);
            }
        } catch (Exception e) {
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
                        people.add((User) object);
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
            dataFile.delete();
            loadStandardData();
        }
    }

    public List<Member> getPeople() {
        return people;
    }

    public List<Item> getItems() {
        return items;
    }
}
