package com.example.p1_rent_a_car.database.repositories;

import com.example.p1_rent_a_car.entities.City;
import com.example.p1_rent_a_car.mappers.CityRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityRepository {
    private JdbcTemplate db;

    public CityRepository(JdbcTemplate db) {
        this.db = db;
    }

    public List<City> getAllCities() {
        var query = "SELECT * FROM Cities";
        return this.db.query(query, (rs, rowNum) -> {
            try {
                return new CityRowMapper().mapRow(rs, rowNum);
            } catch (Exception e) {
                return null;
            }
        });
    }
}
