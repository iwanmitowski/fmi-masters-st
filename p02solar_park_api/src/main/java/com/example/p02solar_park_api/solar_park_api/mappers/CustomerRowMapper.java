package com.example.p02solar_park_api.solar_park_api.mappers;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setNumberOfProjects(rs.getInt("number_of_projects"));

        return customer;
    }
}
