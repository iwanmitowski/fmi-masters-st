package com.example.p02solar_park_api.solar_park_api.services;

import com.example.p02solar_park_api.solar_park_api.entities.Project;
import com.example.p02solar_park_api.solar_park_api.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public boolean create(Project project) {
        return this.projectRepository.create(project);
    }

    public List<Project> getAll() {
        return this.projectRepository.getAll();
    }

    public Project getById(int id) {
        return this.projectRepository.getById(id);
    }

    public List<Project> getByCustomerId(int id) {
        return this.projectRepository.getByCustomerId(id);
    }

    public Project update(Project project) {
        return this.projectRepository.update(project);
    }

    public boolean delete(int id) {
        return this.projectRepository.delete(id);
    }
}
