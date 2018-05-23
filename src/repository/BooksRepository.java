package repository;

import message.StreamResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BooksRepository {
    private static final Map<String, File> dbMap = new HashMap<>();
    private static final BooksRepository instance = new BooksRepository();

    static {
        Scanner db;
        try {
            db = new Scanner(new File("db/titles"));
            while (db.hasNextLine()) {
                String title = db.nextLine();
                dbMap.put(title, new File("db/books/" + title));
            }
            db.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static BooksRepository getInstance() {
        return instance;
    }

    private BooksRepository() {
    }

    public List<StreamResponse> getContent(String title) throws FileNotFoundException {
        Scanner db = new Scanner(dbMap.get(title));
        List<StreamResponse> result = new ArrayList<>();
        while (db.hasNextLine()) {
            String line = db.nextLine();
            result.add(new StreamResponse(title, line));
        }
        db.close();
        return result;
    }
}