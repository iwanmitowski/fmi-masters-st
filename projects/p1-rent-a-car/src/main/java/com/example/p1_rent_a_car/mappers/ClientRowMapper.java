package com.example.p1_rent_a_car.mappers;

import com.example.p1_rent_a_car.entities.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Client(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("address"),
            rs.getString("phone"),
            rs.getInt("age"),
            rs.getBoolean("accident_history")
        );
    }
}
