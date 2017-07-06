package com.example.harsh.aide;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HARSH on 08-05-2017.
 */

public class User implements Serializable {
    String fname,lname,email,key,password;
    ArrayList<String> conversations;

    public ArrayList<String> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<String> conversation) {
        this.conversations = conversation;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", key='" + key + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
