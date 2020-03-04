package com.aries.crawler;

import com.aries.crawler.verticles.DatabaseVerticle;
import com.aries.crawler.verticles.ProducerVerticle;
import io.vertx.core.*;

/**
 * @author arowana
 */
public class Starter {
    public static void main(String[] args) {
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
