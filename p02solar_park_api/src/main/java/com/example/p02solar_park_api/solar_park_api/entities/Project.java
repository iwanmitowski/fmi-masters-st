package com.example.p02solar_park_api.solar_park_api.entities;

public class Project {
    public static final String TABLE = "Projects";
    public static class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String COST = "cost";
        public static final String IS_ACTIVE = "is_active";
        public static final String CUSTOMER_ID = "customer_id";
    }

    private int id;
    private String name;
    private int cost;
    private int customerId;

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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
