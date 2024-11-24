package com.example.p02solar_park_api.solar_park_api.system.db.operations;

import com.example.p02solar_park_api.solar_park_api.system.db.QueryProcessor;

public class DeleteQueryBuilder extends WhereQueryBuilder<DeleteQueryBuilder> {
    private QueryProcessor queryProcessor;
    private String tableName;

    public DeleteQueryBuilder(QueryProcessor queryProcessor, String tableName) {
        super(queryProcessor);
        this.queryProcessor = queryProcessor;
        this.tableName = tableName;

        this.queryProcessor.initNewQueryOperation();
        this.queryProcessor.getQueryBuilder()
            .append("DELETE FROM ")
            .append(tableName);
    }

    public DeleteQueryBuilder set(String columnName, Object value) {
        if (this.queryProcessor.columnCollection().isEmpty()){
            this.queryProcessor.valueCollection().add(", ");
        }

        this.queryProcessor.buildColumnValuePair(columnName);
        this.queryProcessor.setQueryColumnValuePair(columnName, value);

        return this;
    }

    public int delete() {
        return this.queryProcessor.processQuery();
    }
}
