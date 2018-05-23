package message;

import java.io.Serializable;

public class OrderResponse implements Serializable {
    private String title;

    public OrderResponse(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}