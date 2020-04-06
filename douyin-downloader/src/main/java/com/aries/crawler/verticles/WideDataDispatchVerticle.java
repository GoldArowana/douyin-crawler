package com.aries.crawler.verticles;

import com.aries.crawler.model.douyincrawler.DouyinCrawlerLogModel;
import com.aries.crawler.trans.message.DouyinUserInfoMessage;
import com.aries.crawler.trans.message.DouyinVideoInfoMessage;
import com.aries.crawler.trans.message.DouyinWideDataMessage;
import com.aries.crawler.trans.message.SimpleInt64Message;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static com.aries.crawler.trans.EventBusTopic.*;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_SUCCESS_MESSAGE;

/**
 * 这个verticle的职责是：
 * 将受到的数据, 根据状态做不同的处理。
 * <p> status = 0 表示这个数据没被处理过, 要把宽表中的数据拆成两部分, 分别交给用户数据插入器和视频数据插入器来处理。
 * <p> status = 1 表示这个款数据中的用户数据部分已经处理过, 但是视频数据的部分还没处理。需要发给视频数据插入器来处理。
 * <p> status = 2 表示这个款数据中的视频数据部分已经处理过, 但是用户数据的部分还没处理。需要发给用户数据插入器来处理。
 * <p> status = 3 表示这个数据已经处理过, 没有剩余价值了, 不必处理, 忽略就可以了。
 *
 * @author arowana
 */
public class WideDataDispatchVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WideDataDispatchVerticle.class);

    @Override
    public void start() {
        vertx.eventBus().consumer(LOGIC_DOUYIN_WIDEDATA_DISPATCH.getTopic(), this::dispatch).setMaxBufferedMessages(4000);
    }

    private void dispatch(Message<Object> message) {
        var wideDataMessage = (DouyinWideDataMessage) message.body();

        // 如果 用户部分的数据 未处于已处理状态
        if ((wideDataMessage.status() & DouyinCrawlerLogModel.STATUS_USER_DONE) == 0) {
            logger.info("user data need to be parsed, uid:" + wideDataMessage.authorUid());
            processUser(DouyinUserInfoMessage.of(wideDataMessage), wideDataMessage.id());
        }

        // 如果 视频部分的数据 未处于已处理状态
        if ((wideDataMessage.status() & DouyinCrawlerLogModel.STATUS_VIDEO_DONE) == 0) {
            logger.info("video data need to be parsed, awemeid:" + wideDataMessage.awemeId());
            processVideo(DouyinVideoInfoMessage.of(wideDataMessage), wideDataMessage.id());
        }

        message.reply(COMMON_SUCCESS_MESSAGE);

    }

    private void processVideo(DouyinVideoInfoMessage douyinVideoInfoMessage, BigInteger wideDataId) {
        logger.info("prepare to insert video, awemeid: " + douyinVideoInfoMessage.awemeId());
        vertx.eventBus().request(MYSQL_DOUYIN_VIDEO_INSERT.getTopic(), douyinVideoInfoMessage, new DeliveryOptions().setSendTimeout(TimeUnit.SECONDS.toMillis(20)), insertReply -> {
            vertx.executeBlocking(future -> {
                if (insertReply.succeeded()) {
                    logger.info("insert video reply succeeded, awemeid: " + douyinVideoInfoMessage.awemeId());
                    future.complete(100);
                } else {
                    logger.error("insert video reply failed, awemeid: " + douyinVideoInfoMessage.awemeId() + ",cause:" + insertReply.cause());
                }
            }, res -> {
                if (res.result() instanceof Integer s) {
                    if (s.equals(100)) {
                        vertx.eventBus().request(MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_VIDEO.getTopic(), new SimpleInt64Message(wideDataId), updateReply -> {
                            if (updateReply.succeeded()) {
                                logger.info("update status video succ ...");
                            } else {
                                logger.info("update status video fail ...");
                            }
                        });
                    }
                }
            });
        });
    }

    private void processUser(DouyinUserInfoMessage douyinUserInfoMessage, BigInteger wideDataId) {
        logger.info("prepare to insert user, uid:" + douyinUserInfoMessage.uid());
        vertx.eventBus().request(MYSQL_DOUYIN_USER_INSERT.getTopic(), douyinUserInfoMessage, new DeliveryOptions().setSendTimeout(TimeUnit.SECONDS.toMillis(20)), insertReply -> {
            vertx.executeBlocking(future -> {
                if (insertReply.succeeded()) {
                    logger.info("insert user reply succeeded, uid: " + douyinUserInfoMessage.uid());
                    future.complete(100);
                } else {
                    logger.error("insert user reply failed, uid: " + douyinUserInfoMessage.uid() + ",cause:" + insertReply.cause());
                }
            }, res -> {
                if (res.result() instanceof Integer s) {
                    if (s.equals(100)) {
                        vertx.eventBus().request(MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_USER.getTopic(), new SimpleInt64Message(wideDataId), updateReply -> {
                            if (updateReply.succeeded()) {
                                logger.info("update status user succ ...");
                            } else {
                                logger.info("update status user fail ...");
                            }
                        });
                    }
                }
            });
        });
    }
}
