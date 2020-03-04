package com.aries.crawler.verticles;

import ca.krasnay.sqlbuilder.SelectBuilder;
import com.aries.crawler.Model.douyincrawler.DouYinCrawlerLogModel;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.tools.Orm;
import io.vertx.core.AbstractVerticle;
import io.vertx.sqlclient.Tuple;

/**
 * @author arowana
 */
public class ProducerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        System.out.println("ProducerVerticle started...");
        String sql = new SelectBuilder().column("*").from("douyin_crawler_log").toString();
        // Get a connection from the pool
        MySqlExecuteHelper.execute(vertx, sql, Tuple.tuple(), mySQLexecutorRes -> {
            if (mySQLexecutorRes.succeeded()) {
                mySQLexecutorRes.result().forEach(row -> {
                    DouYinCrawlerLogModel model = Orm.getModel(row, DouYinCrawlerLogModel.class);
                    if (model != null) {
                        System.out.println(model.getId());
                    }
                });
            }
        });
    }
}
