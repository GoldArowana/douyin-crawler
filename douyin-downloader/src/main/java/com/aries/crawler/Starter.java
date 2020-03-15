package com.aries.crawler;

import com.aries.crawler.trans.codec.CommonMessageCodec;
import com.aries.crawler.trans.message.*;
import com.aries.crawler.verticles.DatabaseVerticle;
import com.aries.crawler.verticles.WideDataDispatchVerticle;
import com.aries.crawler.verticles.WideDataPickUpVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author arowana
 */
public class Starter {
    private static final Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        // Force to use slf4j. 参考自dgate：https://github.com/DTeam-Top/dgate/blob/master/src/main/java/top/dteam/dgate/Launcher.java
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");

        // 全局vertx
        var vertx = Vertx.vertx();

        // 注册解码器
        vertx.eventBus().registerDefaultCodec(SimpleInt64Message.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(DouyinUserInfoMessage.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(DouyinVideoInfoMessage.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(DouyinWideDataMessage.class, new CommonMessageCodec<>());
        vertx.eventBus().registerDefaultCodec(CommonResponseMessage.class, new CommonMessageCodec<>());

        // 部署 宽表读取器
        var wideDataPickUpFuture = simpleDeploy(vertx, WideDataPickUpVerticle.class);
        // 部署 宽表数据派发器
        var wideDataDispatchFuture = optionalDeploy(vertx, WideDataDispatchVerticle.class, 5);
        // 部署 数据处理器
        var databaseFuture = optionalDeploy(vertx, DatabaseVerticle.class, 5);

        // 检查三个verticle是否都部署成功
        CompositeFuture.all(wideDataPickUpFuture, databaseFuture, wideDataDispatchFuture).setHandler(ar -> {
            if (ar.succeeded()) {
                logger.info("所有Verticle启动完成");
            } else {
                logger.error("至少一个服务器启动失败:{}", ar.cause());
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
                .setInstances(workerSize)
                .setWorkerPoolName(verticleType.getTypeName() + "-pool")
                .setWorkerPoolSize(workerSize);

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
