package com.example.p1_rent_a_car.entities;

public class Client {
    private int id;
    private String name;
    private String address;
    private String phone;
    private int age;
    private boolean accidentHistory;

    public Client(int id, String name, String address, String phone, int age, boolean accidentHistory) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.accidentHistory = accidentHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAccidentHistory() {
        return accidentHistory;
    }

    public void setAccidentHistory(boolean accidentHistory) {
        this.accidentHistory = accidentHistory;
    }
}

