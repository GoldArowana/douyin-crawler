package com.aries.crawler.verticles;

import ca.krasnay.sqlbuilder.SelectBuilder;
import com.aries.crawler.Model.douyincrawler.DouyinCrawlerLogModel;
import com.aries.crawler.eventbus.message.DouyinUserInfoMessage;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.tools.Orm;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.sqlclient.Tuple;

/**
 * @author arowana
 */
public class WideDataProcessProducerVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WideDataProcessProducerVerticle.class);

    @Override
    public void start() {
        String sql = new SelectBuilder().column("*").from("douyin_crawler_log").toString();

        MySqlExecuteHelper.execute(vertx, sql, Tuple.tuple(), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                mysqlExecutorRes.result().forEach(row -> {
                    DouyinCrawlerLogModel model = Orm.getModel(row, DouyinCrawlerLogModel.class);
                    if (model != null) {
                        DouyinUserInfoMessage douyinUserInfoMessage = DouyinUserInfoMessage.of(model);
                        vertx.eventBus().request("mysql.douyin_user.insert", douyinUserInfoMessage, reply -> {
                            if (reply.succeeded()) {
                                logger.info("Received reply succeeded: " + douyinUserInfoMessage.getUid());
                            } else {
                                logger.error("Received reply failed: " + douyinUserInfoMessage.getUid());
                            }
                            logger.info("received reply body: " + reply.result().body());

                        });
                    }
                });
            }
        });
    }
}
