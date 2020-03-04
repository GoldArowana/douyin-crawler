package com.aries.crawler.verticles;

import io.vertx.core.AbstractVerticle;

/**
 * @author arowana
 */
public class DatabaseVerticle extends AbstractVerticle {
    @Override
    public void start() {
        System.out.println("DatabaseVerticle started...");
        vertx.eventBus().consumer("mysql.douyin_user.insert", (message) -> {
            System.out.println(message.body());
        });
    }
}