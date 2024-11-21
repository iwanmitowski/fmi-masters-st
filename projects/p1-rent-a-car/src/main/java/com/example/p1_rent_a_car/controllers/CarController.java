package com.example.p1_rent_a_car.controllers;

import com.example.p1_rent_a_car.entities.Car;
import com.example.p1_rent_a_car.http.AppResponse;
import com.example.p1_rent_a_car.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class CarController {
    private CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @PostMapping("/cars")
    public ResponseEntity<?> create(@RequestBody Car car) {
        try {
            var result = this.service.create(car);

            if (!result) {
                return AppResponse.error()
                    .withMessage("Problem during insert")
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
            }

            return AppResponse.success()
                .withMessage("Car created successfully")
                .withCode(HttpStatus.CREATED)
                .build();
        } catch (IllegalArgumentException e) {
            return AppResponse.success()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

    }

    @GetMapping("/cars/clients/{clientId}")
    public ResponseEntity<?> getAll(@PathVariable int clientId) {
        try {
            var result = (ArrayList<Car>)this.service.getAllCars(clientId);
            return AppResponse.success()
                .withMessage("Successful")
                .withData(result)
                .build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var result = this.service.getById(id);
        if (result == null) {
            return AppResponse.error()
                .withMessage("Problem during obtaining car")
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

        return AppResponse.success()
            .withMessage("Successful")
            .withData(new Car[] { result })
            .build();
    }

    @PutMapping("/cars")
    public ResponseEntity<?> update(@RequestBody Car car) {
        try {
            var result = this.service.update(car);
            if (result == null) {
                return AppResponse.error()
                    .withMessage("Problem during update")
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
            }

            return AppResponse.success()
                .withMessage("Successful")
                .withData(new Car[] { result })
                .build();
        } catch (IllegalArgumentException e) {
            return AppResponse.success()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

    }

    @DeleteMapping("/cars/{id}")
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
    }
}
