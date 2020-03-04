package com.aries.crawler.tools;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.*;

/**
 * 用于获取mysql客户端、连接池
 *
 * @author arowana
 */
public class MySqlClientHelper {
    private static volatile MySQLPool singletonMySQLPool;

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
    public static MySQLPool getClient(Vertx vertx) {
        if (singletonMySQLPool == null) {
            synchronized (MySqlClientHelper.class) {
                if (singletonMySQLPool == null) {
                    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                            .setPort(3306)
                            .setHost("localhost")
                            .setDatabase("douyin_crawler")
                            .setUser("root")
                            .setPassword("1qaz2wsx");

                    PoolOptions poolOptions = new PoolOptions()
                            .setMaxSize(5);

                    singletonMySQLPool = MySQLPool.pool(vertx, connectOptions, poolOptions);
                }
            }
        }
        return singletonMySQLPool;
    }


}
