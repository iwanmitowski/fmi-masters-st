package com.example.p02solar_park_api.solar_park_api.services;

import com.example.p02solar_park_api.solar_park_api.entities.Project;
import com.example.p02solar_park_api.solar_park_api.repositories.IProjectRepository;
//import com.example.p02solar_park_api.solar_park_api.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private IProjectRepository projectRepository;
    private CustomerService customerService;

    public ProjectService(IProjectRepository projectRepository, CustomerService customerService) {
        this.projectRepository = projectRepository;
        this.customerService = customerService;
    }

    public boolean create(Project project) {
        return this.projectRepository.save(project) != null;
    }

    public List<Project> getAll() {
        return this.projectRepository.findAll();
    }

    public Project getById(int id) {
        return this.projectRepository.getById(id);
    }

    public List<Project> getByCustomerId(int customerId) {
        return this.projectRepository.findByCustomer_IdAndIsActive(customerId, true);
    }

    public Project update(Project project) {
        return this.projectRepository.save(project);
    }

    public boolean delete(int id) {
        var project = this.projectRepository.getReferenceById(id);

        if (project != null) {
            return false;
        }

        project.setActive(false);
        return this.projectRepository.save(project) != null;
    }
}
