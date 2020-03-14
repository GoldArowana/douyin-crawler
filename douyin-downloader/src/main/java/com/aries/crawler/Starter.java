package com.aries.crawler;

import com.aries.crawler.eventbus.codec.CommonMessageCodec;
import com.aries.crawler.eventbus.message.CommonResponseMessage;
import com.aries.crawler.eventbus.message.DouyinUserInfoMessage;
import com.aries.crawler.verticles.DatabaseVerticle;
import com.aries.crawler.verticles.WideDataProcessProducerVerticle;
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
        vertx.eventBus().registerDefaultCodec(DouyinUserInfoMessage.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(CommonResponseMessage.class, new CommonMessageCodec<>());

        Future<Void> producerFuture = Future.future(res -> {
            vertx.deployVerticle(WideDataProcessProducerVerticle.class.getTypeName(), deployRes -> {
                if (deployRes.succeeded()) {
                    res.complete();
                } else {
                    res.fail(deployRes.cause());
                }
            });
        });


        DeploymentOptions options = new DeploymentOptions()
                .setWorker(true)
                .setInstances(5) // matches the worker pool size below
                .setWorkerPoolName("data-insert-pool")
                .setWorkerPoolSize(5);

        Future<Void> databaseFuture = Future.future(res -> {
            vertx.deployVerticle(DatabaseVerticle.class.getTypeName(),options, deployRes -> {
                if (deployRes.succeeded()) {
                    res.complete();
                } else {
                    res.fail(deployRes.cause());
                }
            });
        });

        CompositeFuture.all(producerFuture, databaseFuture).setHandler(ar -> {
            if (ar.succeeded()) {
                logger.info("所有Verticle启动完成");
            } else {
                logger.error("至少一个服务器启动失败:{}", ar.cause());
            }
        });
    }
}
