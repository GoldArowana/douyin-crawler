package com.aries.crawler.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author arowana
 */
public class ExampleVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(ExampleVerticle.class);

    // Called when verticle is deployed
    // Verticle部署时调用
    public void start() {
        logger.info("My ... verticle...");
    }

    // Optional - called when verticle is undeployed
    // 可选 - Verticle撤销时调用
    public void stop() {
        logger.info(" stop ...My ... verticle...");
    }

}