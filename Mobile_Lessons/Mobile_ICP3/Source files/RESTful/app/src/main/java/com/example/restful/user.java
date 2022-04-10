package com.example.restful;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class user {

    private int id;

    @SerializedName("Login")
    private String userName;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
}
