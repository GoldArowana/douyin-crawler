package com.aries.crawler.verticles;

import com.aries.crawler.sqlbuilder.InsertBuilder;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.trans.message.DouyinUserInfoMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.aries.crawler.trans.EventBusTopic.MYSQL_DOUYIN_USER_INSERT;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_FAILED_MESSAGE;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_SUCCESS_MESSAGE;

/**
 * @author arowana
 */
public class UserInsertVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(UserInsertVerticle.class);

    @Override
    public void start() {
        // 用于插入用户数据
        vertx.eventBus().consumer(MYSQL_DOUYIN_USER_INSERT.getTopic(), this::mysqlDouyinUserInsertHandler).setMaxBufferedMessages(4000);
    }

    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    private void mysqlDouyinUserInsertHandler(Message<Object> message) {
        var userInfoMessage = (DouyinUserInfoMessage) message.body();

        // 构建sql数据, 插入用户信息。
        var insertBuilder = new InsertBuilder("douyin_user_info")
                .set("uid", userInfoMessage.uid())
                .set("short_id", userInfoMessage.shortId())
                .set("nickname", userInfoMessage.nickname())
                .set("signature", userInfoMessage.signature())
                .set("avatar_larger_url", userInfoMessage.avatarLargerUrl())
                .set("share_url", userInfoMessage.shareUrl())
                .set("share_info_qrcode_url", userInfoMessage.shareInfoQrCodeUrl())
                .onDuplicateKeyUpdate("ut", getDateTimeAsString(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));

        logger.info("user sql:" + insertBuilder.getSql() + "---values:" + insertBuilder.getValues());

        MySqlExecuteHelper.prepareExecute(vertx, insertBuilder.getSql(), insertBuilder.getValues(), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                logger.info("insert user succ, uid:" + userInfoMessage.uid());
                message.reply(COMMON_SUCCESS_MESSAGE);
            } else {
                logger.info("insert user. failed" + mysqlExecutorRes.cause());
                message.reply(COMMON_FAILED_MESSAGE);
            }
        });
    }
}
