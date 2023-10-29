package com.example.entrypointactivity.model.address;

import java.util.ArrayList;

public class ResponseAddress {
   private ArrayList<Address> data;
   private String status;

    public ArrayList<Address> getData() {
        return data;
    }

    public void setData(ArrayList<Address> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
