package com.aries.crawler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author arowana
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface MysqlField {
    /**
     * 对应的数据库字段名
     */
    public String alias();

    /**
     * 对应的类型
     */
    public Class<?> type();
}
