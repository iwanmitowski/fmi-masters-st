package com.example.p02solar_park_api.solar_park_api.system.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryProcessor<T> {
    public final JdbcTemplate jdbcTemplate;
    private StringBuilder queryBuilder;
    private ArrayList<String> columnCollection;
    private ArrayList<String> placeholderCollection;
    private ArrayList<Object> valueCollection;

    public QueryProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StringBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public ArrayList<String> columnCollection() {
        return columnCollection;
    }

    public ArrayList<String> placeholderCollection() {
        return placeholderCollection;
    }

    public ArrayList<Object> valueCollection() {
        return valueCollection;
    }

    public int processQuery() {
        String sqlQuery = this.queryBuilder.toString();
        return this.jdbcTemplate.update(sqlQuery, this.valueCollection.toArray());
    }

    public void setQueryColumnValuePair(String columnName, Object value) {
        columnCollection.add(columnName);
        placeholderCollection.add("?");
        valueCollection.add(value);
    }

    public void buildColumnValuePair(String columnName, String operator){
        this.queryBuilder.append(columnName)
                .append(operator)
                .append("?");
    }

    public void buildColumnValuePair(String columnName){
        this.buildColumnValuePair(columnName, "=");
    }

    public void initNewQueryOperation(){
        this.columnCollection = new ArrayList<>();
        this.placeholderCollection = new ArrayList<>();
        this.valueCollection = new ArrayList<>();
        this.queryBuilder = new StringBuilder();
    }

    public List<T> processSelectList(RowMapper<T> mapper) {
        String sqlQuery = this.queryBuilder.toString();
        return this.jdbcTemplate.query(sqlQuery, this.valueCollection.toArray(), mapper);
    }
}
