package com.example.p1_rent_a_car.database.repositories;

import com.example.p1_rent_a_car.entities.Client;
import com.example.p1_rent_a_car.mappers.ClientRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository {
    private JdbcTemplate db;

    public ClientRepository(JdbcTemplate db) {
        this.db = db;
    }

    public Client getById(int id) {
        var query = "SELECT * from Clients where id = ?";
        var clients = this.db.query(query, new Object[]{id}, (rs, rowNum) -> {
            try {
                return new ClientRowMapper().mapRow(rs, rowNum);
            } catch (Exception e) {
                return null;
            }
        });

        return clients.isEmpty() ? null : clients.get(0);
    }
}
