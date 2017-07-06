package com.example.harsh.aide;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HARSH on 08-05-2017.
 */

public class Aide implements Serializable {
    String fname,lname,email,key,password;
    String phone,address,job_profile,job_description,image;
    ArrayList<String> conversations;
    ArrayList<String> apointments;

    public ArrayList<String> getApointments() {
        return apointments;
    }

    public void setApointments(ArrayList<String> apointments) {
        this.apointments = apointments;
    }

    public ArrayList<String> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<String> conversations) {
        this.conversations = conversations;
    }

    //this is the aide file

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob_profile() {
        return job_profile;
    }

    public void setJob_profile(String job_profile) {
        this.job_profile = job_profile;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
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
        return "Aide{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", key='" + key + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", job_profile='" + job_profile + '\'' +
                ", job_description='" + job_description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
