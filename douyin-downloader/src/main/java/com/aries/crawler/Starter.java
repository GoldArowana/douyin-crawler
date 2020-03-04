package com.aries.crawler;

import com.aries.crawler.verticles.DatabaseVerticle;
import com.aries.crawler.verticles.ProducerVerticle;
import io.vertx.core.*;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author arowana
 */
public class Starter {
    private static final Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        //Force to use slf4j
        // 参考自dgate：https://github.com/DTeam-Top/dgate/blob/master/src/main/java/top/dteam/dgate/Launcher.java
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");

        Vertx vertx = Vertx.vertx();

        Future<Void> producerFuture = Future.future(res -> {
            vertx.deployVerticle(ProducerVerticle.class.getTypeName(), deployRes -> {
                if (deployRes.succeeded()) {
                    res.complete();
                } else {
                    res.fail(deployRes.cause());
                }
            });
        });


        Future<Void> databaseFuture = Future.future(res -> {
            vertx.deployVerticle(DatabaseVerticle.class.getTypeName(), deployRes -> {
                if (deployRes.succeeded()) {
                    res.complete();
                } else {
                    res.fail(deployRes.cause());
                }
            });
        });


        CompositeFuture.all(producerFuture, databaseFuture).setHandler(ar -> {
            if (ar.succeeded()) {
                System.out.println("所有Verticle启动完成");
            } else {
                System.err.println("至少一个服务器启动失败:" + ar.cause());
            }
        });
    }
}
