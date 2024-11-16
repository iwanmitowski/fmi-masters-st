package com.example.p1_rent_a_car.entities;

public class Car {
    private int id;
    private String make;
    private String model;
    private int year;
    private boolean availability;
    private double pricePerDay;

    public Car(int id, String make, String model, int year, boolean availability, double pricePerDay) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.availability = availability;
        this.pricePerDay = pricePerDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
