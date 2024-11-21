package com.example.p1_rent_a_car.mappers;

import com.example.p1_rent_a_car.entities.Car;
import com.example.p1_rent_a_car.entities.Client;
import com.example.p1_rent_a_car.entities.Offer;
import com.example.p1_rent_a_car.entities.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        var client = new Client(
            rs.getInt("client_id"),
            rs.getString("client_name"),
            rs.getString("client_address"),
            rs.getString("client_phone"),
            rs.getInt("client_age"),
            rs.getBoolean("client_accident_history")
        );

        var status = new Status(
            rs.getInt("status_id"),
            rs.getString("status_name")
        );

        var car = new Car(
                rs.getInt("id"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("manufactured_year"),
                rs.getBoolean("availability"),
                rs.getInt("price_per_day"),
                rs.getInt("city_id"),
                null,
                rs.getBoolean("is_deleted")
        );

        return new Offer(
            rs.getInt("id"),
            rs.getInt("client_id"),
            client,
            rs.getInt("car_id"),
            car,
            rs.getString("start_date"),
            rs.getString("end_date"),
            rs.getDouble("total_price"),
            rs.getInt("status_id"),
            status,
            rs.getString("offer_date"),
            rs.getBoolean("is_deleted")
        );
    }
}
