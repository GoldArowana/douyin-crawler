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
import java.util.function.Consumer;

import static com.aries.crawler.trans.EventBusTopic.LOGIC_DOUYIN_VIDEO_DOWNLOAD;

/**
 * @author arowana
 */
public class VideoDataPickUpVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WideDataPickUpVerticle.class);

    private Consumer<Long> consumer = (offset) -> {
        var sql = new SelectBuilder()
                .column("*")
                .from(DouyinVideoModel.TABLE)
                .where(" status = " + DouyinVideoModel.STATUS_VIDEO_DOWNLOAD_DEFAULT)
                .limit(1L)
                .offset(offset)
                .orderBy("ct", false)
                .toString();

        logger.info("构建pick up sql: " + sql);
        MySqlExecuteHelper.prepareExecute(vertx, sql, new ArrayList<>(), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                List<JsonObject> rows = mysqlExecutorRes.result().getRows();
                for (JsonObject row : rows) {
                    var model = Orm.getModel(row, DouyinVideoModel.class);
                    DouyinVideoInfoMessage douyinVideoInfoMessage = DouyinVideoInfoMessage.of(model);
                    vertx.eventBus().request(LOGIC_DOUYIN_VIDEO_DOWNLOAD.getTopic(), douyinVideoInfoMessage, updateReply -> {
                        if (updateReply.succeeded()) {
                            logger.info("download video succ ...");
                        } else {
                            logger.info("download video fail ...");
                        }
                    });
                }
            } else {
                logger.error("execute download video failed: " + mysqlExecutorRes.cause());
            }
        });

    };


    @Override
    public void start() {
        vertx.setPeriodic(5000, id -> {
            consumer.accept(0L);
        });


//        vertx.setPeriodic(2000, id -> vertx.executeBlocking(future -> {
//            consumer.accept(0L);
//            consumer.accept(5L);
//            consumer.accept(10L);
//        }, res -> {
        // nothing
//        }));
    }
}
