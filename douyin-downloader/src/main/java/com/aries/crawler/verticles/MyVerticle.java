package com.aries.crawler.verticles;

import io.vertx.core.AbstractVerticle;

/**
 * @author arowana
 */
public class MyVerticle extends AbstractVerticle {

    // Called when verticle is deployed
    // Verticle部署时调用
    public void start() {
        System.out.println("My ... verticle...");
    }

    // Optional - called when verticle is undeployed
    // 可选 - Verticle撤销时调用
    public void stop() {
        System.out.println(" stop ...My ... verticle...");
    }

}