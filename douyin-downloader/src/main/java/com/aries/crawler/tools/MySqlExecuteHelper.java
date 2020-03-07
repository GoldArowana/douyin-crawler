package com.aries.crawler.tools;

import com.aries.crawler.Starter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;

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
    public static void execute(Vertx vertx, String sql, Tuple arguments, Handler<AsyncResult<RowSet<Row>>> handler) {
        MySqlClientHelper.getClient(vertx).getConnection(connectionHandlerRes -> {
            if (connectionHandlerRes.succeeded()) {
                SqlConnection connection = connectionHandlerRes.result();
                connection.preparedQuery(sql, arguments, handler);
                connection.close();
            } else {
                logger.error("Could not connect: " + connectionHandlerRes.cause().getMessage());
            }
        });
    }
}
