package com.example.p02solar_park_api.solar_park_api.system.db;

import com.example.p02solar_park_api.solar_park_api.system.db.operations.DeleteQueryBuilder;
import com.example.p02solar_park_api.solar_park_api.system.db.operations.InsertQueryBuilder;
import com.example.p02solar_park_api.solar_park_api.system.db.operations.SelectQueryBuilder;
import com.example.p02solar_park_api.solar_park_api.system.db.operations.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class QueryBuilder<T> {
    private QueryProcessor<T> queryProcessor;

    public QueryBuilder(QueryProcessor queryProcessor) {
        this.queryProcessor = queryProcessor;
    }

    public SelectQueryBuilder<T> select(String ...cols) {
        return new SelectQueryBuilder<T>(queryProcessor, cols);
    }

    public SelectQueryBuilder<T> selectAll() {
        return new SelectQueryBuilder<T>(queryProcessor, "*");
    }

    public InsertQueryBuilder into(String tableName) {
        return new InsertQueryBuilder(this.queryProcessor, tableName);
    }

    public UpdateQueryBuilder updateTable(String tableName) {
        return new UpdateQueryBuilder(this.queryProcessor, tableName);
    }

    public DeleteQueryBuilder delete(String tableName) {
        return new DeleteQueryBuilder(this.queryProcessor, tableName);
    }


}
