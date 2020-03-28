package com.aries.crawler.verticles;

import com.aries.crawler.model.douyincrawler.DouyinVideoModel;
import com.aries.crawler.sqlbuilder.SelectBuilder;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.tools.Orm;
import com.aries.crawler.trans.message.DouyinVideoInfoMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author arowana
 */
public class VideoDataPickUpVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WideDataPickUpVerticle.class);

    private Supplier<Void> supplier = () -> {
        var sql = new SelectBuilder()
                .column("*")
                .from(DouyinVideoModel.TABLE)
                .where(" status = " + DouyinVideoModel.STATUS_VIDEO_DOWNLOAD_DEFAULT)
                .limit(1L)
                .orderBy("ct", false)
                .toString();

        logger.info("构建pick up sql: " + sql);
        MySqlExecuteHelper.prepareExecute(vertx, sql, new ArrayList<>(), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                List<JsonObject> rows = mysqlExecutorRes.result().getRows();
                for (JsonObject row : rows) {
                    var model = Orm.getModel(row, DouyinVideoModel.class);
                    DouyinVideoInfoMessage douyinVideoInfoMessage = DouyinVideoInfoMessage.of(model);
                    System.out.println(douyinVideoInfoMessage);
                }
            } else {
                logger.error("execute pick up sql failed: " + mysqlExecutorRes.cause());
            }
        });

        return null;
    };


    @Override
    public void start() {
        vertx.setPeriodic(1000, id -> {
            supplier.get();
        });
    }
}
