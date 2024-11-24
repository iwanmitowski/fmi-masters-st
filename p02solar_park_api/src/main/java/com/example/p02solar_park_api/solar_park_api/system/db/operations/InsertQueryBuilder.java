package com.example.p02solar_park_api.solar_park_api.system.db.operations;

import com.example.p02solar_park_api.solar_park_api.system.db.QueryProcessor;

public class InsertQueryBuilder {
    private QueryProcessor queryProcessor;
    private String tableName;

    public InsertQueryBuilder(QueryProcessor queryProcessor, String tableName) {
        this.queryProcessor = queryProcessor;
        this.tableName = tableName;
        this.queryProcessor.initNewQueryOperation();
        this.queryProcessor.getQueryBuilder()
            .append("INSERT INTO ")
            .append(tableName);
    }

    public boolean insert() {
        var columnDefinition = String.join(",", this.queryProcessor.columnCollection());
        var valueDefinition = String.join(",", this.queryProcessor.placeholderCollection());

        this.queryProcessor.getQueryBuilder()
                .append("(").append(columnDefinition).append(") ")
                .append("VALUES ").append("(").append(valueDefinition).append(")");

        var resultCount = this.queryProcessor.processQuery();

        return resultCount > 0;
    }

    public InsertQueryBuilder withValue(String columnName, Object value) {
        this.queryProcessor.setQueryColumnValuePair(columnName, value);

        return this;
    }
}
