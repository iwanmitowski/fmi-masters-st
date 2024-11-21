package com.example.p1_rent_a_car.database.repositories;

import com.example.p1_rent_a_car.entities.Offer;
import com.example.p1_rent_a_car.mappers.OfferRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OfferRepository {
    private final JdbcTemplate db;

    public OfferRepository(JdbcTemplate db) {
        this.db = db;
    }

    public boolean create(Offer offer) {
        var query = "INSERT INTO offers (client_id, car_id, start_date, end_date, total_price, status_id, offer_date) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        var rowsAffected = this.db.update(query,
            offer.getClientId(),
            offer.getCarId(),
            offer.getStartDate(),
            offer.getEndDate(),
            offer.getTotalPrice(),
            offer.getStatusId(),
            offer.getOfferDate()
        );

        return rowsAffected > 0;
    }

    public List<Offer> getAll(int clientId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT o.id, o.start_date, o.end_date, o.total_price, o.offer_date, o.is_deleted, ");
        queryBuilder.append("c.id AS client_id, c.name AS client_name, c.phone AS client_phone, c.age AS client_age, c.address AS client_address, c.accident_history as client_accident_history, ");
        queryBuilder.append("cr.id AS car_id, cr.make AS car_make, cr.model AS car_model, cr.manufactured_year, cr.availability, cr.price_per_day, cr.city_id, ");
        queryBuilder.append("st.id AS status_id, st.status_name AS status ");
        queryBuilder.append("FROM offers o ");
        queryBuilder.append("JOIN clients c ON o.client_id = c.id ");
        queryBuilder.append("JOIN cars cr ON o.car_id = cr.id ");
        queryBuilder.append("JOIN status st ON o.status_id = st.id ");
        queryBuilder.append("WHERE cr.is_deleted = false and o.client_id = ?");

        var query = queryBuilder.toString();

        return this.db.query(query, new Object[]{clientId}, (rs, rowNum) -> new OfferRowMapper().mapRow(rs, rowNum));
    }

    public Offer getById(int id) {
        var queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT o.id, o.start_date, o.end_date, o.total_price, o.offer_date, o.is_deleted, ");
        queryBuilder.append("c.id AS client_id, c.name AS client_name, c.phone AS client_phone, c.age AS client_age, c.address AS client_address, c.accident_history as client_accident_history, ");
        queryBuilder.append("cr.id AS car_id, cr.make AS car_make, cr.model AS car_model, cr.price_per_day, cr.city_id, ");
        queryBuilder.append("st.id AS status_id, st.status_name AS status ");
        queryBuilder.append("FROM offers o ");
        queryBuilder.append("JOIN clients c ON o.client_id = c.id ");
        queryBuilder.append("JOIN cars cr ON o.car_id = cr.id ");
        queryBuilder.append("JOIN status st ON o.status_id = st.id ");
        queryBuilder.append("WHERE o.id = ? AND cr.is_deleted = false AND o.is_deleted = false");

        var query = queryBuilder.toString();

        var offers = this.db.query(query, new Object[]{id}, (rs, rowNum) -> new OfferRowMapper().mapRow(rs, rowNum));
        return offers.isEmpty() ? null : offers.get(0);
    }

    public boolean updateStatus(int offerId, int statusId) {
        var query = "UPDATE offers SET status_id = ? WHERE id = ?";
        int rowsAffected = this.db.update(query, statusId, offerId);

        return rowsAffected > 0;
    }

    public boolean delete(int id) {
        var query = "UPDATE Offers SET is_deleted = true WHERE id = ?";
        int rows = this.db.update(query, id);

        return rows > 0;
    }
}
