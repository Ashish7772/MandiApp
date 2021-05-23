package com.example.mandiapp;

public class cropModel {
    private String serial;
    private String string;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public cropModel(String serial, String string) {
        this.serial = serial;
        this.string = string;
    }


}
