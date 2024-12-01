package com.example.p02solar_park_api.solar_park_api.services;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
//import com.example.p02solar_park_api.solar_park_api.repositories.CustomerRepository;
import com.example.p02solar_park_api.solar_park_api.repositories.ICustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean create(Customer customer) {
        return this.customerRepository.save(customer) != null;
    }

    public List<Customer> getAll() {
        return this.customerRepository.findByActive(true);
    }

    public Customer getById(int id) {
        return this.customerRepository.findByIdAndActive(id, true);
    }

    public Customer update(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public boolean delete(int id) {
        var customer = this.customerRepository.getReferenceById(id);

        if (customer == null) {
            return false;
        }

        customer.setActive(false);
        return this.customerRepository.save(customer) != null;
    }
}
