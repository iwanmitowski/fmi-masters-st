package com.example.p02solar_park_api.solar_park_api.entities;

public class Customer {

    public static final String TABLE = "Customers";
    public static class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String NUMBER_OF_PROJECTS = "number_of_projects";
        public static final String IS_ACTIVE = "is_active";
    }

    private int id;
    private String name;
    private int numberOfProjects;

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

    public int getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(int numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }
}
