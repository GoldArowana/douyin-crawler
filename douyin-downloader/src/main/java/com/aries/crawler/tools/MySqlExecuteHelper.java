package com.aries.crawler.tools;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;

/**
 * @author arowana
 */
public class MySqlExecuteHelper {
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
    public static void execute(Vertx vertx, String sql, Tuple arguments, Handler<AsyncResult<RowSet<Row>>> handler) {
        MySqlClientHelper.getClient(vertx).getConnection(connectionHandlerRes -> {
            if (connectionHandlerRes.succeeded()) {
                SqlConnection connection = connectionHandlerRes.result();
                connection.preparedQuery(sql, arguments, handler);
            } else {
                System.out.println("Could not connect: " + connectionHandlerRes.cause().getMessage());
            }
        });
    }
}
