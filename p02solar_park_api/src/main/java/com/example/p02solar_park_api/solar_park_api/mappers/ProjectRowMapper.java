package com.example.p02solar_park_api.solar_park_api.mappers;

import com.example.p02solar_park_api.solar_park_api.entities.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt(Project.Columns.ID));
        project.setName(rs.getString(Project.Columns.NAME));
        project.setCost(rs.getInt(Project.Columns.COST));

        return project;
    }
}
