package message;

import java.io.Serializable;

public class StreamResponse implements Serializable {
    private String title;
    private String sentence;

    public StreamResponse(String title, String sentence) {
        this.title = title;
        this.sentence = sentence;
    }

    public String getTitle() {
        return title;
    }

    public String getSentence() {
        return sentence;
    }
}