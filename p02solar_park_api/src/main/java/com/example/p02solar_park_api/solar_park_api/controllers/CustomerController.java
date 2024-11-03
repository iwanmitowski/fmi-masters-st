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

        return ResponseUtil.respond("message", "Customer created successfully", 201);
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAll() {
        var result = (ArrayList<Customer>)this.service.getAllCustomers();
        return ResponseUtil.respond("data", result, 200);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var result = this.service.getById(id);
        if (result == null) {
            return ResponseUtil.respond("message", "entity", 404);
        }
        return ResponseUtil.respond("data", result, 200);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        this.service.delete(id);

        return ResponseUtil.respond("message", "successful", 200);
    }
}
