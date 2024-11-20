package com.example.p02solar_park_api.solar_park_api.repositories;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import com.example.p02solar_park_api.solar_park_api.mappers.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {
    private JdbcTemplate db;

    public CustomerRepository(JdbcTemplate db) {
        this.db = db;
    }

    public boolean create(Customer customer) {
        String query = String.format(
                "INSERT INTO Customers (name) " +
                        "VALUES ('%s');",
                customer.getName()
        );
        this.db.execute(query);

        return true;
    }

    public List<Customer> getAll() {
        var query = "SELECT * FROM Customers WHERE is_active = true";
        return this.db.query(query, (rs, rowNum) -> {
            try {
                return new CustomerRowMapper().mapRow(rs, rowNum);
            } catch (Exception e) {
                return null;
            }
        });
    }

    public Customer getById(int id) {
        var query = "SELECT * FROM Customers WHERE id = ?";

        var customers = this.db.query(query, new Object[]{id}, (rs, rowNum) -> {
            try {
                return new CustomerRowMapper().mapRow(rs, rowNum);
            } catch (Exception e) {
                return null;
            }
        });

        return customers.isEmpty() ? null : customers.get(0);
    }

    public Customer update(Customer customer) {
        var updateQuery = "UPDATE Customers SET name = ?, number_of_projects = ? WHERE is_active = true AND id = ?";
        int rows = this.db.update(updateQuery, customer.getName(), customer.getNumberOfProjects(), customer.getId());

        if (rows == 0) {
            return null;
        }

        return this.getById(customer.getId());
    }

    public boolean delete(int id) {
        var query = "UPDATE Customers SET is_active = false WHERE id = ?";
        int rows = this.db.update(query, id);

        return rows > 0;
    }
}
