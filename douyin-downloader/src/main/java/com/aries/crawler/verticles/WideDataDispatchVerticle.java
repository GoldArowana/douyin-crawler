package com.aries.crawler.verticles;

import com.aries.crawler.model.douyincrawler.DouyinCrawlerLogModel;
import com.aries.crawler.trans.message.DouyinUserInfoMessage;
import com.aries.crawler.trans.message.DouyinWideDataMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

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
        vertx.eventBus().consumer("logic.widedata.dispatch", this::dispatch);
    }

    private void dispatch(Message<Object> message) {
        var wideDataMessage = (DouyinWideDataMessage) message.body();
        logger.debug(wideDataMessage);

        // 如果 用户部分的数据 未处于已处理状态
        if ((wideDataMessage.getStatus() & DouyinCrawlerLogModel.STATUS_USER_DONE) == 0) {
            var douyinUserInfoMessage = DouyinUserInfoMessage.of(wideDataMessage);
            vertx.eventBus().request("mysql.douyin_user.insert", douyinUserInfoMessage, reply -> {
                if (reply.succeeded()) {
                    logger.info("Received reply succeeded, uid: " + douyinUserInfoMessage.getUid());
                } else {
                    logger.error("Received reply failed, uid: " + douyinUserInfoMessage.getUid());
                }
            });
        }

        // 如果 视频部分的数据 未处于已处理状态
        if ((wideDataMessage.getStatus() & DouyinCrawlerLogModel.STATUS_VIDEO_DONE) == 0) {
            var douyinUserInfoMessage = DouyinUserInfoMessage.of(wideDataMessage);
            vertx.eventBus().request("mysql.douyin_video.insert", douyinUserInfoMessage, reply -> {
                if (reply.succeeded()) {
                    logger.info("Received reply succeeded, uid: " + douyinUserInfoMessage.getUid());
                } else {
                    logger.error("Received reply failed, uid: " + douyinUserInfoMessage.getUid());
                }
            });
        }
    }
}
