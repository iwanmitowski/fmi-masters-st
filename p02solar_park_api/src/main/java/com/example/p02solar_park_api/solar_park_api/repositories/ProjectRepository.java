//package com.example.p02solar_park_api.solar_park_api.repositories;
//
//import com.example.p02solar_park_api.solar_park_api.entities.Project;
//import com.example.p02solar_park_api.solar_park_api.mappers.ProjectRowMapper;
//import com.example.p02solar_park_api.solar_park_api.system.db.QueryBuilder;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class ProjectRepository implements IProjectRepository {
//    private final QueryBuilder<Project> db;
//
//    public ProjectRepository(QueryBuilder<Project> db) {
//        this.db = db;
//    }
//
//    public boolean create(Project project) {
//        return this.db
//            .into(Project.TABLE)
//            .withValue(Project.Columns.NAME, project.getName())
//            .withValue(Project.Columns.COST, project.getCost())
//            .withValue(Project.Columns.CUSTOMER_ID, project.getCustomerId())
//            .insert();
//    }
//
//    public List<Project> getAll() {
//        return this.db.selectAll()
//            .from(Project.TABLE)
//            .where(Project.Columns.IS_ACTIVE, true)
//            .fetchAll(new ProjectRowMapper());
//    }
//
//    public Project getById(int id) {
//        return this.db.selectAll().from(Project.TABLE)
//            .where(Project.Columns.IS_ACTIVE, true)
//            .andWhere(Project.Columns.ID, id)
//            .fetch(new ProjectRowMapper());
//    }
//
//    public List<Project> getByCustomerId(int id) {
//        return this.db.selectAll()
//            .from(Project.TABLE)
//            .where(Project.Columns.IS_ACTIVE, true)
//            .andWhere(Project.Columns.CUSTOMER_ID, id)
//            .fetchAll(new ProjectRowMapper());
//    }
//
//    public Project update(Project project) {
//        var rows = this.db.updateTable(Project.TABLE)
//                .set(Project.Columns.NAME, project.getName())
//                .set(Project.Columns.COST, project.getCost())
//                .where(Project.Columns.IS_ACTIVE, true)
//                .andWhere(Project.Columns.ID, project.getId())
//                .update();
//
//        if (rows == 0) {
//            return null;
//        }
//
//        return this.getById(project.getId());
//    }
//
//    public boolean delete(int id) {
//        var rows = this.db.updateTable(Project.TABLE)
//            .set(Project.Columns.IS_ACTIVE, false)
//            .where(Project.Columns.ID, id)
//            .update();
//
//        return rows > 0;
//    }
//}
