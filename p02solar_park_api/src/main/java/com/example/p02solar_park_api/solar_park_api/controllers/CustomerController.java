package com.example.p02solar_park_api.solar_park_api.controllers;

import com.example.p02solar_park_api.solar_park_api.entities.Customer;
import com.example.p02solar_park_api.solar_park_api.entities.Project;
import com.example.p02solar_park_api.solar_park_api.http.AppResponse;
import com.example.p02solar_park_api.solar_park_api.services.CustomerService;
import com.example.p02solar_park_api.solar_park_api.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class CustomerController {

    private CustomerService customerService;
    private ProjectService projectService;


    public CustomerController(
        CustomerService customerService,
        ProjectService projectService) {
        this.customerService = customerService;
        this.projectService = projectService;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        var result = this.customerService.create(customer);

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
        var result = (ArrayList<Customer>)this.customerService.getAll();
        return AppResponse.success()
            .withMessage("Successful")
            .withData(result)
            .build();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var result = this.customerService.getById(id);
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

    @GetMapping("/customers/{id}/projects")
    public ResponseEntity<?> getCustomerProjects(@PathVariable int id) {
        var result = (ArrayList<Project>)this.projectService.getByCustomerId(id);
        return AppResponse.success()
            .withMessage("Successful")
            .withData(result)
            .build();
    }

    @PutMapping("/customers")
    public ResponseEntity<?> update(@RequestBody Customer customer) {
        var result = this.customerService.update(customer);
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
        var result = this.customerService.delete(id);

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
