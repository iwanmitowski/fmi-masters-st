package com.example.p02solar_park_api.solar_park_api.repositories;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import com.example.p02solar_park_api.solar_park_api.mappers.CustomerRowMapper;
import com.example.p02solar_park_api.solar_park_api.system.db.QueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CustomerRepository {
    private final QueryBuilder<Customer> db;

    public CustomerRepository(QueryBuilder<Customer> db) {
        this.db = db;
    }

    public boolean create(Customer customer) {
        return this.db
            .into(Customer.TABLE)
            .withValue(Customer.Columns.NAME, customer.getName())
            .insert();

//        return this.db.insert(Customer.TABLE, new HashMap<>(){{
//            put(Customer.Columns.NAME, customer.getName());
//        }});

//        String query = String.format(
//                "INSERT INTO Customers (name) " +
//                        "VALUES ('%s');",
//                customer.getName()
//        );
//        this.db.execute(query);
//
//        return true;
    }

    public List<Customer> getAll() {
        return this.db.selectAll()
            .from(Customer.TABLE)
            .where(Customer.Columns.IS_ACTIVE, true)
            .fetchAll(new CustomerRowMapper());
    }

    public Customer getById(int id) {
        return this.db.selectAll().from(Customer.TABLE)
            .where(Customer.Columns.IS_ACTIVE, true)
            .andWhere(Customer.Columns.ID, id)
            .fetch(new CustomerRowMapper());

//        var query = "SELECT * FROM Customers WHERE id = ?";
//
//        var customers = this.db.query(query, new Object[]{id}, (rs, rowNum) -> {
//            try {
//                return new CustomerRowMapper().mapRow(rs, rowNum);
//            } catch (Exception e) {
//                return null;
//            }
//        });
//
//        return customers.isEmpty() ? null : customers.get(0);
    }

    public Customer update(Customer customer) {
        var rows = this.db.updateTable(Customer.TABLE)
            .set(Customer.Columns.NAME, customer.getName())
            .set(Customer.Columns.NUMBER_OF_PROJECTS, customer.getNumberOfProjects())
            .where(Customer.Columns.IS_ACTIVE, true)
            .andWhere(Customer.Columns.ID, customer.getId())
            .update();

        if (rows == 0) {
            return null;
        }

        return this.getById(customer.getId());
        //        var updateQuery = "UPDATE Customers SET name = ?, number_of_projects = ? WHERE is_active = true AND id = ?";
        //        int rows = this.db.update(updateQuery, customer.getName(), ustomer.getNumberOfProjects(), customer.getId());
        //
        //        if (rows == 0) {
        //            return null;
        //        }
        //
        //        return this.getById(customer.getId());
    }

    public boolean delete(int id) {
        var rows = this.db.updateTable(Customer.TABLE)
            .set(Customer.Columns.IS_ACTIVE, false)
            .where(Customer.Columns.ID, id)
            .update();

        return rows > 0;
    }
}
