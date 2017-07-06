package com.example.harsh.aide;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HARSH on 20-05-2017.
 */

public class conversation implements Serializable {
    String to,from,key;
    ArrayList<String> messages;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }
}
