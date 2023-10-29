package com.example.entrypointactivity.model.post;

import com.example.entrypointactivity.model.address.Address;

import java.util.ArrayList;

public class ResponsePost {
    private ArrayList<Post> data;
    private String status;

    public ArrayList<Post> getData() {
        return data;
    }

    public void setData(ArrayList<Post> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
