package com.example.p02solar_park_api.solar_park_api.controllers;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import com.example.p02solar_park_api.solar_park_api.http.AppResponse;
import com.example.p02solar_park_api.solar_park_api.services.CustomerService;
import com.example.p02solar_park_api.solar_park_api.utils.ResponseUtil;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class CustomerController {

    private CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        var result = this.service.createCustomer(customer);

        if (!result) {
            return AppResponse.error()
                .withMessage("Problem during insert")
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

        return AppResponse.success()
            .withMessage("Customer created successfully")
            .withCode(HttpStatus.CREATED)
            .build();
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAll() {
        var result = (ArrayList<Customer>)this.service.getAllCustomers();
        return AppResponse.success()
            .withMessage("Successful")
            .withData(result)
            .build();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var result = this.service.getById(id);
        if (result == null) {
            return AppResponse.error()
                .withMessage("Problem during obtaining customer")
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

        return AppResponse.success()
            .withMessage("Successful")
            .withData(new Customer[] { result })
            .build();
    }

    @PutMapping("/customers")
    public ResponseEntity<?> update(@RequestBody Customer customer) {
        var result = this.service.update(customer);
        if (result == null) {
            return AppResponse.error()
                .withMessage("Problem during update")
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

        return AppResponse.success()
            .withMessage("Successful")
            .withData(new Customer[] { result })
            .build();
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        var result = this.service.delete(id);

        if (!result) {
            return AppResponse.error()
                .withMessage("Problem during delete")
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

        return AppResponse.success()
            .withMessage("Successful")
            .build();
//        return ResponseUtil.respond("message", "successful", 200);
    }
}
