package com.example.p02solar_park_api.solar_park_api.system.db.operations;

import com.example.p02solar_park_api.solar_park_api.system.db.QueryProcessor;

public class UpdateQueryBuilder extends WhereQueryBuilder<UpdateQueryBuilder> {
    private QueryProcessor queryProcessor;
    private String tableName;

    public UpdateQueryBuilder(QueryProcessor queryProcessor, String tableName) {
        super(queryProcessor);
        this.queryProcessor = queryProcessor;
        this.tableName = tableName;
        this.queryProcessor.initNewQueryOperation();
        this.queryProcessor.getQueryBuilder()
            .append("UPDATE ")
            .append(tableName)
            .append(" SET ");
    }

    public UpdateQueryBuilder set(String columnName, Object value) {
        if(!this.queryProcessor.columnCollection().isEmpty()) {
            this.queryProcessor.getQueryBuilder().append(",");
        }

        this.queryProcessor.buildColumnValuePair(columnName);
        this.queryProcessor.setQueryColumnValuePair(columnName, value);
        return this;

    }

    public int update() {
        return this.queryProcessor.processQuery();
    }
}
