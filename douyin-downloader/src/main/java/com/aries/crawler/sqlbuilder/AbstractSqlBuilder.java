package com.aries.crawler.sqlbuilder;

import java.util.List;

/**
 * @author arowana
 */
public abstract class AbstractSqlBuilder {

    protected void appendList(StringBuilder sql, List<?> list, String init, String sep) {

        boolean first = true;

        for (Object s : list) {
            if (first) {
                sql.append(init);
            } else {
                sql.append(sep);
            }
            sql.append(s);
            first = false;
        }
    }

    protected String repeat(String ch, Integer times, String sep) {
        StringBuilder result = new StringBuilder(ch);
        if (times > 1) {
            result.append((sep + ch).repeat(times - 1));
        }
        return result.toString();
    }

}
