package com.aries.crawler.tools;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sql.ResultSet;

import java.util.List;

/**
 * @author arowana
 */
public class MySqlExecuteHelper {
    private static final Logger logger = LoggerFactory.getLogger(MySqlExecuteHelper.class);

    /**
     * 防止实例化
     */
    private MySqlExecuteHelper() {

    }

    /**
     * @param vertx     全局vertx
     * @param sql       要执行的sql语句
     * @param arguments sql参数
     * @param handler   回调
     */
    public static void prepareExecute(Vertx vertx, String sql, List<Object> arguments, Handler<AsyncResult<ResultSet>> handler) {
        var jdbcClient = MySqlClientHelper.getJDBcClient(vertx);
        // 构造参数
        JsonArray params = new JsonArray();

        for (Object argument : arguments) {
            params.add(argument);
        }

        // 执行查询
        jdbcClient.queryWithParams(sql, params, handler);
    }

    public static void execute(Vertx vertx, String sql, Handler<AsyncResult<ResultSet>> handler) {
        logger.debug("准备执行sql: " + sql);
        MySqlClientHelper.getJDBcClient(vertx).getConnection(connectionHandlerRes -> {
            if (connectionHandlerRes.succeeded()) {
                var connection = connectionHandlerRes.result();
                connection.query(sql, handler);
                connection.close();
            } else {
                logger.error("Could not connect: " + connectionHandlerRes.cause().getMessage());
            }
        });
    }
}
