package com.example.p02solar_park_api.solar_park_api.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Project.TABLE)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
public class Project {
    public static final String TABLE = "Projects";
    public static class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String COST = "cost";
        public static final String IS_ACTIVE = "is_active";
        public static final String CUSTOMER_ID = "customer_id";
    }

    @Id
    @Column(name = Columns.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = Columns.NAME)
    private String name;
    @Column(name = Columns.COST)
    private double cost;
//    @Column(name = Columns.CUSTOMER_ID)
//    private int customerId;
    @ManyToOne
    @JoinColumn(name = Columns.CUSTOMER_ID)
    private Customer customer;
    @Column(name = Columns.IS_ACTIVE)
    private boolean isActive = true;
}
