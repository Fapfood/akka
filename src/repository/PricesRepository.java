package repository;

import exeption.NotFoundInFileException;
import message.SearchResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PricesRepository {
    private static final File db1File = new File("db/db_1");
    private static final File db2File = new File("db/db_2");
    private static final Map<Integer, File> dbMap = new HashMap<>();
    private static final PricesRepository instance = new PricesRepository();

    static {
        dbMap.put(1, db1File);
        dbMap.put(2, db2File);
    }


    public static PricesRepository getInstance() {
        return instance;
    }

    private PricesRepository() {
    }

    public SearchResponse searchForTitleIn(String title, Integer dbNumber) throws FileNotFoundException, NotFoundInFileException {
        Scanner db = new Scanner(dbMap.get(dbNumber));
        while (db.hasNextLine()) {
            String line = db.nextLine();
            String[] splitted = line.split(";");
            String lineTitle = splitted[0];
            Double price = Double.valueOf(splitted[1]);
            if (title.equals(lineTitle)) {
                db.close();
                return new SearchResponse(title, price);
            }
        }
        db.close();
        throw new NotFoundInFileException();
    }
}