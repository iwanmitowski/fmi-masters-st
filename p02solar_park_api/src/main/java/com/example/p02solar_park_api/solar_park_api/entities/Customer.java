package com.example.p02solar_park_api.solar_park_api.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Customer.TABLE)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
public class Customer {
    public static final String TABLE = "Customers";
    public static class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String NUMBER_OF_PROJECTS = "number_of_projects";
        public static final String IS_ACTIVE = "is_active";
    }

    @Id
    @Column(name = Columns.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = Columns.NAME)
    private String name;
    @Column(name = Columns.NUMBER_OF_PROJECTS)
    private int numberOfProjects;
    @Column(name = Columns.IS_ACTIVE)
    private boolean active = true;
    @OneToMany(mappedBy = "customer")
    private List<Project> projects = new ArrayList<>();
}
