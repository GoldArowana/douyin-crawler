package com.aries.crawler.verticles;

import com.aries.crawler.eventbus.message.CommonResponseMessage;
import com.aries.crawler.eventbus.message.DouyinUserInfoMessage;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.sqlbuilder.InsertBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.sqlclient.Tuple;

import java.time.LocalDateTime;
import java.util.List;

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

        InsertBuilder ib = new InsertBuilder("douyin_user_info")
                .set("uid", userInfoMessage.getUid())
                .set("short_id", userInfoMessage.getShortId())
                .set("nickname", userInfoMessage.getNickname())
                .set("signature", userInfoMessage.getSignature())
                .set("avatar_larger_url", userInfoMessage.getAvatarLargerUrl())
                .set("share_url", userInfoMessage.getShareUrl())
                .set("share_info_qrcode_url", userInfoMessage.getShareInfoQrCodeUrl())
                .onDuplicateKeyUpdate("ut", LocalDateTime.now());
        String sql = ib.getSql();
        List<Object> values = ib.getValues();

//        logger.info("insert sql:" + sql + "\nvalues:" + values);

        MySqlExecuteHelper.execute(vertx, sql, Tuple.tuple(values), mysqlExecutorRes -> {
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
                        .message("failed, " + mysqlExecutorRes.cause())
                        .build();
                message.reply(response);
            }
        });
    }

}