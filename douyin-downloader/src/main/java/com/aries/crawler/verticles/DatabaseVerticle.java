package com.aries.crawler.verticles;

import ca.krasnay.sqlbuilder.InsertBuilder;
import com.aries.crawler.eventbus.message.CommonResponseMessage;
import com.aries.crawler.eventbus.message.DouyinUserInfoMessage;
import com.aries.crawler.tools.MySqlExecuteHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.sqlclient.Tuple;

import java.time.LocalDateTime;

/**
 * @author arowana
 */
public class DatabaseVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

    @Override
    public void start() {
        vertx.eventBus().consumer("mysql.douyin_user.insert", this::mysqlDouyinUserInsertHandler);
    }

    private void mysqlDouyinUserInsertHandler(Message<Object> message) {
        DouyinUserInfoMessage userInfoMessage = (DouyinUserInfoMessage) message.body();

        String sql = new InsertBuilder("douyin_user_info")
                .set("uid", String.format("'%d'", userInfoMessage.getUid()))
                .set("short_id", String.format("'%d'", userInfoMessage.getShortId()))
                .set("nickname", String.format("'%s'", userInfoMessage.getNickname()))
                .set("signature", String.format("'%s'", userInfoMessage.getSignature()))
                .set("avatar_larger_url", String.format("'%s'", userInfoMessage.getAvatarLargerUrl()))
                .set("share_url", String.format("'%s'", userInfoMessage.getShareUrl()))
                .set("share_info_qrcode_url", String.format("'%s'", userInfoMessage.getShareInfoQrCodeUrl()))
                .toString();

        sql += " on duplicate key update ut = '" + LocalDateTime.now() + "'";

        logger.debug("insert sql:" + sql);

        MySqlExecuteHelper.execute(vertx, sql, Tuple.tuple(), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                var response = CommonResponseMessage.CommonResponseMessageBuilder
                        .aCommonResponseMessage()
                        .code(100)
                        .message("success")
                        .build();
                message.reply(response);
            } else {
                var response = CommonResponseMessage.CommonResponseMessageBuilder
                        .aCommonResponseMessage()
                        .code(1000)
                        .message("failed")
                        .build();
                message.reply(response);
                logger.error("insert user info failed. ", mysqlExecutorRes.cause());
            }
        });
    }

}