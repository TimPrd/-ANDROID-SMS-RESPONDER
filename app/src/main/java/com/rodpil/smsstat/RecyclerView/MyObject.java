package com.rodpil.smsstat.RecyclerView;

public class MyObject {
    private String name;
    private String phone;
    private String counter;

    public MyObject(String name, String phone, String counter) {
        this.name = name;
        this.phone = phone;
        this.counter = counter;
    }

    public String getCounter() {
        return counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //getters & setters
}