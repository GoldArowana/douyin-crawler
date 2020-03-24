package com.aries.crawler.tools;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

/**
 * 用于获取mysql客户端、连接池
 *
 * @author arowana
 */
public class MySqlClientHelper {
    private static volatile JDBCClient jdbcClient;

    /**
     * 防止实例化
     */
    private MySqlClientHelper() {
    }

    /**
     * 双重校验, 单例模式, 创建mysql连接池
     *
     * @param vertx 全局vertx
     * @return 数据库连接池
     */
    public static JDBCClient getJDBcClient(Vertx vertx) {
        if (jdbcClient == null) {
            synchronized (MySqlClientHelper.class) {
                if (jdbcClient == null) {
                    JsonObject dbConfig = new JsonObject();
                    dbConfig.put("url", "jdbc:mysql://localhost:3306/douyin_crawler");
                    dbConfig.put("driver_class", "com.mysql.jdbc.Driver");
                    dbConfig.put("user", "root");
                    dbConfig.put("password", "1qaz2wsx"); // 反正是localhost, 密码随便看
//                    dbConfig.put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider");
                    dbConfig.put("maximumPoolSize", 200);
                    dbConfig.put("cachePrepStmts", true);
                    dbConfig.put("prepStmtCacheSize", 250);
                    dbConfig.put("prepStmtCacheSqlLimit", 2048);
                    jdbcClient = JDBCClient.createShared(vertx, dbConfig, "my-data-pool¬");
                }
            }
        }
        return jdbcClient;
    }

}
