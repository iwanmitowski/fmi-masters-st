package com.example.p1_rent_a_car.mappers;

import com.example.p1_rent_a_car.entities.Car;
import com.example.p1_rent_a_car.entities.City;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        var city = new City(
            rs.getInt("city_id"),
            rs.getString("city_name")
        );

        return new Car(
            rs.getInt("id"),
            rs.getString("make"),
            rs.getString("model"),
            rs.getInt("manufactured_year"),
            rs.getBoolean("availability"),
            rs.getInt("price_per_day"),
            rs.getInt("city_id"),
            city,
            rs.getBoolean("is_deleted")
        );
    }
}
