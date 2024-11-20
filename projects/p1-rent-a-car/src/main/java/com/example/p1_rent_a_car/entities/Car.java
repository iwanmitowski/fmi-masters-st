package com.example.p1_rent_a_car.entities;

public class Car {
    private int id;
    private String make;
    private String model;
    private int year;
    private boolean availability;
    private double pricePerDay;
    private boolean isDeleted;
    private int cityId;
    private City city;

    public Car() {
    }

    public Car(int id, String make, String model, int year, boolean availability, double pricePerDay, int cityId, City city) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.availability = availability;
        this.pricePerDay = pricePerDay;
        this.cityId = cityId;
        this.city = city;
    }

    public Car(int id, String make, String model, int year, boolean availability, double pricePerDay, int cityId, City city, boolean isDeleted) {
        this(id, make, model, year, availability, pricePerDay, cityId, city);
        this.isDeleted = isDeleted;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        isDeleted = isDeleted;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
