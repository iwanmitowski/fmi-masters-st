package com.example.p02solar_park_api.solar_park_api.services;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class CustomerService {

    private JdbcTemplate db;

    public CustomerService(JdbcTemplate db) {
        this.db = db;
    }

    public boolean createCustomer(Customer customer) {
        String query = String.format(
            "INSERT INTO Customers (name) " +
            "VALUES ('%s');",
            customer.getName()
        );
        this.db.execute(query);

        return true;
    }

    public List<Customer> getAllCustomers() {
        var query = "SELECT * FROM Customers";
        return this.db.query(query, (rs, rowNum) -> {
            try {
                return mapEntity(rs, rowNum);
            } catch (Exception e) {
                return null;
            }
        });
    }

    public Customer getById(int id) {
        var query = "SELECT * FROM Customers WHERE id = ?";

        List<Customer> customers = (List<Customer>) this.db.query(query, new Object[]{id}, (rs, rowNum) -> {
            try {
                return mapEntity(rs, rowNum);
            } catch (Exception e) {
                return null;
            }
        }).get(0);

        return customers.isEmpty() ? null : customers.get(0);
    }

    public void delete(int id) {
        var query = "UPDATE Customers SET is_active = false WHERE id = ?";
        this.db.update(query, id);
    }

    private Customer mapEntity(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setNumberOfProjects(rs.getInt("number_of_projects"));
        return customer;
    }
}
