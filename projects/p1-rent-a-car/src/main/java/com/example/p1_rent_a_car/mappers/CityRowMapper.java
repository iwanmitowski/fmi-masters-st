package com.example.p1_rent_a_car.mappers;

import com.example.p1_rent_a_car.entities.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityRowMapper implements RowMapper<City> {
    @Override
    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new City(
            rs.getInt("id"),
            rs.getString("name")
        );
    }
}
