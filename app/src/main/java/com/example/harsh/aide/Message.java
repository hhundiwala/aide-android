package com.example.harsh.aide;

import java.util.ArrayList;

/**
 * Created by HARSH on 21-04-2017.
 */

public class Message {
    String meg_key,sent_by,imgurl,message;
    Long time;
    ArrayList<String> comments;



    public Message(String meg_key, String sent_by, String imgurl, String message, Long time) {
        this.meg_key = meg_key;
        this.sent_by = sent_by;
        this.imgurl = imgurl;
        this.message = message;
        this.time = time;
        comments = new ArrayList<>();
    }

    public Message() {
        comments = new ArrayList<>();
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMeg_key() {
        return meg_key;
    }

    public void setMeg_key(String meg_key) {
        this.meg_key = meg_key;
    }

    public String getSent_by() {
        return sent_by;
    }

    public void setSent_by(String sent_by) {
        this.sent_by = sent_by;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }
}
