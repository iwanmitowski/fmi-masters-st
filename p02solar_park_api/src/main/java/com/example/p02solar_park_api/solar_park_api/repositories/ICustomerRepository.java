package com.example.p02solar_park_api.solar_park_api.repositories;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByActive(boolean active);
    Customer findByIdAndActive(int id, boolean active);

}
