package message;

import java.io.Serializable;

public class StreamRequest implements Serializable {
    private String title;

    public StreamRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}