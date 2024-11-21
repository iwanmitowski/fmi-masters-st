package com.example.p1_rent_a_car.entities;

public class Offer {
    private int id;
    private int clientId;
    private Client client;
    private int carId;
    private Car car;
    private String startDate;
    private String endDate;
    private double totalPrice;
    private int statusId;
    private Status status;
    private String offerDate;
    private boolean isDeleted;

    public Offer() {
    }

    public Offer(int id, int clientId, Client client, int carId, Car car, String startDate, String endDate, double totalPrice, int statusId, Status status, String offerDate, boolean isDeleted) {
        this(id, clientId, carId, startDate, endDate, totalPrice, statusId, offerDate);
        this.status = status;
        this.client = client;
        this.car = car;
    }

    public Offer(int id, int clientId, int carId, String startDate, String endDate, double totalPrice, int statusId, String offerDate) {
        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.statusId = statusId;
        this.offerDate = offerDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
