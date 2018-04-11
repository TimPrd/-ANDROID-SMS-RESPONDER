package com.rodpil.smsstat.Contact;

public class Person {

    private String name, phone, counter;

    public Person(String name, String phone, String counter) {
        this.name = name;
        this.phone = phone;
        this.counter = counter;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCounter() {
        return counter;
    }
}
