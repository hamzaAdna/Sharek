package com.example.entrypointactivity.model.user;

import com.example.entrypointactivity.model.address.Address;

import java.util.ArrayList;

public class ResponseUser {
    private ArrayList<User> data;
    private String status;

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
