package com.example.p02solar_park_api.solar_park_api.repositories;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import com.example.p02solar_park_api.solar_park_api.entities.Project;
import com.example.p02solar_park_api.solar_park_api.mappers.ProjectRowMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByCustomer_IdAndIsActive(int customer_id, boolean isActive);
}
