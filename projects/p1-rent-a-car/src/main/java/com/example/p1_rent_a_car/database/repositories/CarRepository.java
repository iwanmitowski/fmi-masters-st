package com.example.p1_rent_a_car.database.repositories;

import com.example.p1_rent_a_car.entities.Car;
import com.example.p1_rent_a_car.mappers.CarRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {
    private JdbcTemplate db;

    public CarRepository(JdbcTemplate db) {
        this.db = db;
    }

    public boolean create(Car car) {
        var query = "INSERT INTO Cars (make, model, manufactured_year, availability, price_per_day, city_id, is_deleted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        int rowsAffected = this.db.update(query, new Object[]{
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.isAvailability(),
                car.getPricePerDay(),
                car.getCityId(),
                car.getIsDeleted()
        });

        return rowsAffected > 0;
    }

    public List<Car> getAllCars(int cityId) {
        var queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT c.id, c.make, c.model, c.manufactured_year, c.availability, ");
        queryBuilder.append("c.price_per_day, c.is_deleted, ct.id AS city_id, ct.name AS city_name ");
        queryBuilder.append("FROM Cars c JOIN Cities ct ON c.city_id = ct.id ");
        queryBuilder.append("WHERE c.is_deleted = false and c.city_id = ?");

        var query = queryBuilder.toString();
        return this.db.query(query, new Object[]{cityId}, (rs, rowNum) -> {
            try {
                return new CarRowMapper().mapRow(rs, rowNum);
            } catch (Exception e) {
                return null;
            }
        });
    }

    public Car getById(int id) {
        var queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT c.id, c.make, c.model, c.manufactured_year, c.availability, ");
        queryBuilder.append("c.price_per_day, c.is_deleted, ct.id AS city_id, ct.name AS city_name ");
        queryBuilder.append("FROM Cars c JOIN Cities ct ON c.city_id = ct.id ");
        queryBuilder.append("WHERE c.is_deleted = false AND c.id = ?");

        var query = queryBuilder.toString();
        var cars = this.db.query(query, new Object[]{id}, (rs, rowNum) -> {
            try {
                return new CarRowMapper().mapRow(rs, rowNum);
            } catch (Exception e) {
                return null;
            }
        });

        return cars.isEmpty() ? null : cars.get(0);
    }


    public Car update(Car car) {
        var updateQuery = "UPDATE Cars SET make = ?, model = ?, manufactured_year = ?, availability = ?, price_per_day = ?, city_id = ? " +
                "WHERE is_deleted = false AND id = ?";

        int rows = this.db.update(updateQuery,
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.isAvailability(),
                car.getPricePerDay(),
                car.getCityId(),
                car.getId()
        );

        if (rows == 0) {
            return null;
        }

        return this.getById(car.getId());
    }

    public boolean delete(int id) {
        var query = "UPDATE Cars SET is_deleted = true WHERE id = ?";
        int rows = this.db.update(query, id);

        return rows > 0;
    }
}
