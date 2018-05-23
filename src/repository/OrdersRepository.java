package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrdersRepository {
    private static final OrdersRepository instance = new OrdersRepository();
    private PrintWriter out;

    public static OrdersRepository getInstance() {
        return instance;
    }

    private OrdersRepository() {
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("db/orders", true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveOrder(String title) {
        out.println(title);
        out.flush();
    }

    public void close() {
        out.close();
    }
}