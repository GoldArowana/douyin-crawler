package com.aries.crawler.verticles;

import com.aries.crawler.sqlbuilder.InsertBuilder;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.trans.message.DouyinUserInfoMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.sqlclient.Tuple;

import java.time.LocalDateTime;

import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_FAILED_MESSAGE;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_SUCCESS_MESSAGE;

/**
 * @author arowana
 */
public class DatabaseVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

    @Override
    public void start() {
        // 用于插入用户数据
        vertx.eventBus().consumer("mysql.douyin_user.insert", this::mysqlDouyinUserInsertHandler);
        // 用于插入视频数据
        vertx.eventBus().consumer("mysql.douyin_video.insert", this::mysqlDouyinVideoInsertHandler);
    }

    private void mysqlDouyinUserInsertHandler(Message<Object> message) {
        var userInfoMessage = (DouyinUserInfoMessage) message.body();

        // 构建sql数据, 插入用户信息。
        var insertBuilder = new InsertBuilder("douyin_user_info")
                .set("uid", userInfoMessage.getUid())
                .set("short_id", userInfoMessage.getShortId())
                .set("nickname", userInfoMessage.getNickname())
                .set("signature", userInfoMessage.getSignature())
                .set("avatar_larger_url", userInfoMessage.getAvatarLargerUrl())
                .set("share_url", userInfoMessage.getShareUrl())
                .set("share_info_qrcode_url", userInfoMessage.getShareInfoQrCodeUrl())
                .onDuplicateKeyUpdate("ut", LocalDateTime.now());

        MySqlExecuteHelper.execute(vertx, insertBuilder.getSql(), Tuple.tuple(insertBuilder.getValues()), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                message.reply(COMMON_SUCCESS_MESSAGE);
            } else {
                message.reply(COMMON_FAILED_MESSAGE);
            }
        });
    }

    private void mysqlDouyinVideoInsertHandler(Message<Object> message) {
        System.out.println("---video");
        System.out.println(message);
    }
}