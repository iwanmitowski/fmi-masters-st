package com.example.p02solar_park_api.solar_park_api.controllers;

import com.example.p02solar_park_api.solar_park_api.entities.Project;
import com.example.p02solar_park_api.solar_park_api.http.AppResponse;
import com.example.p02solar_park_api.solar_park_api.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ProjectController {
    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping("/projects")
    public ResponseEntity<?> create(@RequestBody Project project) {
        var result = this.service.create(project);

        if (!result) {
            return AppResponse.error()
                .withMessage("Problem during insert")
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
    }

        return AppResponse.success()
            .withMessage("Project created successfully")
            .withCode(HttpStatus.CREATED)
            .build();
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getAll() {
        var result = (ArrayList<Project>)this.service.getAll();
        return AppResponse.success()
            .withMessage("Successful")
            .withData(result)
            .build();
    }

    @GetMapping("/projects/customers/{customerId}")
    public ResponseEntity<?> getByCustomerId(int customerId) {
        var result = (ArrayList<Project>)this.service.getByCustomerId(customerId);
        return AppResponse.success()
                .withMessage("Successful")
                .withData(result)
                .build();
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var result = this.service.getById(id);
        if (result == null) {
            return AppResponse.error()
                .withMessage("Problem during obtaining project")
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

        return AppResponse.success()
            .withMessage("Successful")
            .withData(new Project[] { result })
            .build();
    }

    @PutMapping("/projects")
    public ResponseEntity<?> update(@RequestBody Project project) {
        var result = this.service.update(project);
        if (result == null) {
            return AppResponse.error()
                .withMessage("Problem during update")
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }

        return AppResponse.success()
            .withMessage("Successful")
            .withData(new Project[] { result })
            .build();
    }

    @DeleteMapping("/projects/{id}")
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
