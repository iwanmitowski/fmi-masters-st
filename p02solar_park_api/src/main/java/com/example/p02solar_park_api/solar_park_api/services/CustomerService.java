package com.example.p02solar_park_api.solar_park_api.services;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import com.example.p02solar_park_api.solar_park_api.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean createCustomer(Customer customer) {
        return this.customerRepository.create(customer);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.getAll();
    }

    public Customer getById(int id) {
        return this.customerRepository.getById(id);
    }

    public Customer update(Customer customer) {
        return this.customerRepository.update(customer);
    }

    public boolean delete(int id) {
        return this.customerRepository.delete(id);
    }
}
