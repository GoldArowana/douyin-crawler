package com.aries.crawler.sqlbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arowana
 */
public class DeleteBuilder extends AbstractSqlBuilder {

    private String table;

    private List<String> wheres = new ArrayList<>();

    public DeleteBuilder(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder("delete from ").append(table);
        appendList(sql, wheres, " where ", " and ");
        return sql.toString();
    }

    public DeleteBuilder where(String expr) {
        wheres.add(expr);
        return this;
    }

}
