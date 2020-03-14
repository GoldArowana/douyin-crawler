package com.aries.crawler.sqlbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arowana
 */
public class InsertBuilder extends AbstractSqlBuilder {
    private String table;

    private List<String> columns = new ArrayList<>();
    private List<Object> values = new ArrayList<>();

    private List<String> upColumns = new ArrayList<>();
    private List<Object> upValues = new ArrayList<>();

    public InsertBuilder(String table) {
        this.table = table;
    }

    public InsertBuilder set(String column, Object value) {
        columns.add(column);
        values.add(value);
        return this;
    }

    public InsertBuilder onDuplicateKeyUpdate(String column, Object value) {
        upColumns.add(column);
        upValues.add(value);
        return this;
    }

    public List<Object> getValues() {
        var result = new ArrayList<>();
        result.addAll(values);
        result.addAll(upValues);
        return result;
    }

    public String getSql() {
        var sql = new StringBuilder("insert into ")
                .append(table).append(" (");
        appendList(sql, columns, "", ", ");
        sql.append(") values (");
        sql.append(repeat("?", columns.size(), ","));
        sql.append(")");

        if (!upColumns.isEmpty()) {
            sql.append(" on duplicate key update ");
            for (int i = 0; i < upColumns.size(); i++) {
                if (i == 0) {
                    sql.append(upColumns.get(i) + " = ?");
                } else {
                    sql.append(", " + upColumns.get(i) + " = ?");
                }
            }
        }
        return sql.toString();
    }

}
