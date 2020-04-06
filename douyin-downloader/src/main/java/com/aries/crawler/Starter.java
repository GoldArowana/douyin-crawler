package com.aries.crawler;

import com.aries.crawler.trans.codec.CommonMessageCodec;
import com.aries.crawler.trans.message.*;
import com.aries.crawler.verticles.*;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author arowana
 */
public class Starter {
    private static final Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        // Force to use slf4j. 参考自dgate：https://github.com/DTeam-Top/dgate/blob/master/src/main/java/top/dteam/dgate/Launcher.java
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");

        var vertx = Vertx.vertx();

        logger.info("register codec");
        vertx.eventBus().registerCodec(new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(SimpleInt64Message.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(DouyinUserInfoMessage.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(DouyinVideoInfoMessage.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(DouyinWideDataMessage.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(CommonResponseMessage.class, new CommonMessageCodec<>());

        logger.info("deploying verticles");
        List<Future> futures = new ArrayList<>() {
            {
                add(optionalDeploy(vertx, UserInsertVerticle.class, 1));
                add(optionalDeploy(vertx, VideoInsertVerticle.class, 1));
                add(optionalDeploy(vertx, UpdateDataVerticle.class, 1));
                add(optionalDeploy(vertx, WideDataDispatchVerticle.class, 1));
                add(optionalDeploy(vertx, WideDataPickUpVerticle.class, 1));
                add(optionalDeploy(vertx, VideoDataPickUpVerticle.class, 1));
                add(optionalDeploy(vertx, VideoDownloadVerticle.class, 1));
            }
        };

        CompositeFuture.all(futures).setHandler(ar -> {
            if (ar.succeeded()) {
                logger.info("all verticle start");
            } else {
                logger.error("verticle(s) failed: " + ar.cause());
            }
        });
    }

    /**
     * 简单部署
     *
     * @param vertx        全局vertx
     * @param verticleType 部署的verticel类
     * @return 部署后的Future回调
     */
    public static Future<Void> simpleDeploy(Vertx vertx, Class<?> verticleType) {
        return Future.future(res -> {
            vertx.deployVerticle(verticleType.getTypeName(), deployRes -> {
                if (deployRes.succeeded()) {
                    res.complete();
                } else {
                    res.fail(deployRes.cause());
                }
            });
        });
    }

    /**
     * 配置化部署
     *
     * @param vertx        全局vertx
     * @param verticleType 部署的verticel类
     * @param workerSize   实例个数
     * @return 部署后的Future回调
     */
    public static Future<Void> optionalDeploy(Vertx vertx, Class<?> verticleType, int workerSize) {
        var options = new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolName(verticleType.getTypeName() + "-pool")
                .setWorkerPoolSize(workerSize)
                .setMaxWorkerExecuteTime(TimeUnit.SECONDS.toNanos(10))
                .setInstances(workerSize);

        return Future.future(res -> {
            vertx.deployVerticle(verticleType.getTypeName(), options, deployRes -> {
                if (deployRes.succeeded()) {
                    res.complete();
                } else {
                    res.fail(deployRes.cause());
                }
            });
        });
    }
}
