package com.endassignment.data;

import com.endassignment.exceptions.SaveDataToFileException;
import com.endassignment.exceptions.UnknownObjectException;
import com.endassignment.model.Item;
import com.endassignment.model.Member;
import com.endassignment.model.User;
import org.apache.commons.io.FileDeleteStrategy;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final File DATA_FILE = new File("src/main/resources/com/endassignment/ui/data.dat");
    private final List<Member> people = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public Database() {
        //check for data file
        if (!DATA_FILE.exists()) {
            loadStandardData();
            System.out.println("Loaded standard data");
        } else {
            load();
        }
    }

    private void loadStandardData() {
        //create standard data
        try {
            Files.createFile(DATA_FILE.toPath());
        } catch (FileAlreadyExistsException ignored) {
            System.out.println("File could not be deleted, overwriting data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Created new data file");

        //add Data
        addPeople();
        addItems();
        addBorrowedItems();

        save();
    }

    private void addPeople() {
        people.add(new User(1, "Ilene", "Ilene", "Skinner", "Ilene123", LocalDate.of(1958, 4, 13)));
        people.add(new User(2, "Terell", "Terell", "Park", "Terell123", LocalDate.of(1959, 10, 16)));
        people.add(new User(3, "Lavonne", "Lavonne", "Henderson", "Lavonne123", LocalDate.of(1960, 5, 3)));
        people.add(new User(4, "Steve", "Steve", "Melcorn", "Steve123", LocalDate.of(1963, 6, 2)));
        people.add(new Member(5, "Eustace", "Faulkner", LocalDate.of(1960, 5, 3)));
        people.add(new Member(6, "Malakai", "Haig", LocalDate.of(1971, 1, 27)));
        people.add(new Member(7, "Odetta", "Sempers", LocalDate.of(1979, 3, 23)));
        people.add(new User(8, "Riordan", "Riordan", "Lane", "Riordan123", LocalDate.of(1979, 10, 21)));
        people.add(new User(9, "Terry", "Terry", "Brasher", "Terry123", LocalDate.of(1982, 7, 31)));
        people.add(new User(10, "Charla", "Charla", "Upton", "Charla123", LocalDate.of(2001, 12, 7)));
        people.add(new Member(11, "Lorrie", "Barnes", LocalDate.of(1982, 7, 31)));
        people.add(new Member(12, "Lulu", "Law", LocalDate.of(1966, 3, 20)));
        people.add(new Member(13, "Ellie", "Leonard", LocalDate.of(1999, 3, 13)));

    }

    private void addItems() {
        items.add(new Item(1, "Absalom, Absalom!", "William Faulkner", true));
        items.add(new Item(2, "A time to kill", "John Grisham", true));
        items.add(new Item(3, "The house of mirth", "Edith Wharton", true));
        items.add(new Item(4, "East of eden", "John SteinBeck", true));
        items.add(new Item(5, "The sun also rises", "Ernest Hemingway", true));
        items.add(new Item(6, "Vile bodies", "Evelyn Waugh", true));
    }

    private void addBorrowedItems() {
        people.get(4).getBorrowedItems().put(items.get(0), LocalDate.of(2022, 9, 9));
        people.get(7).getBorrowedItems().put(items.get(4), LocalDate.of(2021, 12, 29));
        items.get(0).setAvailable(false);
        items.get(4).setAvailable(false);
    }


    public void save() {
        System.out.println("Saving data to file");
        //save list to file
        try (FileOutputStream fos = new FileOutputStream(DATA_FILE, false);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Object object : people) {
                oos.writeObject(object);
            }
            for (Object object : items) {
                oos.writeObject(object);
            }
        } catch (Exception e) {
            throw new SaveDataToFileException(e);
        }
    }

    private void load() {
        //load list from file
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            loadObjects(ois);
            System.out.println("Loaded data from file");
        } catch (IOException e) {
            try {
                FileDeleteStrategy.FORCE.delete(DATA_FILE);
            } catch (IOException ignored) {
            } //ignore and overwrite current file
            finally {
                loadStandardData();
            }
        } catch (ClassNotFoundException e) {
            throw new UnknownObjectException();
        }
    }

    private void loadObjects(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        while (true) {
            try {
                Object object = ois.readObject();
                if (object instanceof Member member) {//check for objects found in file
                    people.add(member);
                } else if (object instanceof Item item) {
                    items.add(item);
                } else {
                    throw new UnknownObjectException();
                }
            } catch (EOFException e) {
                return;
            } catch (ClassNotFoundException e) {
                throw new UnknownObjectException(e);
            }
        }
    }

    public List<Member> getPeople() {
        return people;
    }

    public List<Item> getItems() {
        return items;
    }
}
