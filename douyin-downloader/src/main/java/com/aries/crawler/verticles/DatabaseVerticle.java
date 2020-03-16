package com.aries.crawler.verticles;

import com.aries.crawler.sqlbuilder.InsertBuilder;
import com.aries.crawler.sqlbuilder.UpdateBuilder;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.trans.message.DouyinUserInfoMessage;
import com.aries.crawler.trans.message.DouyinVideoInfoMessage;
import com.aries.crawler.trans.message.SimpleInt64Message;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.aries.crawler.trans.EventBusTopic.*;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_FAILED_MESSAGE;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_SUCCESS_MESSAGE;

/**
 * @author arowana
 */
public class DatabaseVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    @Override
    public void start() {
        // 用于插入用户数据
        vertx.eventBus().consumer(MYSQL_DOUYIN_USER_INSERT.getTopic(), this::mysqlDouyinUserInsertHandler);
        // 用于插入视频数据
        vertx.eventBus().consumer(MYSQL_DOUYIN_VIDEO_INSERT.getTopic(), this::mysqlDouyinVideoInsertHandler);
        // 更新款表的status状态为'已处理用户数据'状态
        vertx.eventBus().consumer(MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_USER.getTopic(), this::mysqlDouyinWideDataUpdateStatusUser);
        // 更新款表的status状态为'已处理视频数据'状态
        vertx.eventBus().consumer(MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_VIDEO.getTopic(), this::mysqlDouyinWideDataUpdateStatusVideo);
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
                .onDuplicateKeyUpdate("ut", getDateTimeAsString(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));

        logger.info("user sql:" + insertBuilder.getSql() + "---values:" + insertBuilder.getValues());

        MySqlExecuteHelper.prepareExecute(vertx, insertBuilder.getSql(), insertBuilder.getValues(), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                logger.info("insert user succ, uid:" + userInfoMessage.getUid());
                message.reply(COMMON_SUCCESS_MESSAGE);
            } else {
                logger.info("insert user. failed" + mysqlExecutorRes.cause());
                message.reply(COMMON_FAILED_MESSAGE);
            }
        });
    }

    private void mysqlDouyinVideoInsertHandler(Message<Object> message) {
        var videoInfoMessage = (DouyinVideoInfoMessage) message.body();
        // 构建sql数据, 插入视频信息。
        var insertBuilder = new InsertBuilder("douyin_video_info")
                .set("id", videoInfoMessage.getAwemeId())
                .set("comments", videoInfoMessage.getAwemeDesc())
                .set("create_time", videoInfoMessage.getAwemeCreateTime())
                .set("uid", videoInfoMessage.getAuthorUid())
                .set("cover_url", videoInfoMessage.getVideoCoverUrl())
                .set("dynamic_cover_url", videoInfoMessage.getVideoDynamicCoverUrl())
                .set("download_addr_url", videoInfoMessage.getVideoDownloadAddrUrl())
                .set("share_url", videoInfoMessage.getVideoShareUrl())
                .set("tag", videoInfoMessage.getVideoVideoTag())
                .set("duration", videoInfoMessage.getVideoDuration())
                .onDuplicateKeyUpdate("ut", getDateTimeAsString(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));
        logger.info("video sql:" + insertBuilder.getSql() + "---values:" + insertBuilder.getValues());

        MySqlExecuteHelper.prepareExecute(vertx, insertBuilder.getSql(), insertBuilder.getValues(), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                logger.info("insert video succ, awemeid:" + videoInfoMessage.getAwemeId());
                message.reply(COMMON_SUCCESS_MESSAGE);
            } else {
                logger.info("insert video. failed:" + mysqlExecutorRes.cause());
                message.reply(COMMON_FAILED_MESSAGE);
            }
        });
    }

    private void mysqlDouyinWideDataUpdateStatusUser(Message<Object> message) {
        var idMessage = (SimpleInt64Message) message.body();

        // 构建sql数据, 插入用户信息。
        var insertBuilder = new UpdateBuilder("douyin_crawler_log")
                .set("status = status | 1")
                .where("id=" + idMessage.getId())
                .toString();

        MySqlExecuteHelper.execute(vertx, insertBuilder, mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                message.reply(COMMON_SUCCESS_MESSAGE);
            } else {
                message.reply(COMMON_FAILED_MESSAGE);
            }
        });
    }

    private void mysqlDouyinWideDataUpdateStatusVideo(Message<Object> message) {
        var idMessage = (SimpleInt64Message) message.body();

        // 构建sql数据, 插入用户信息。
        var insertBuilder = new UpdateBuilder("douyin_crawler_log")
                .set("status = status | 2")
                .where("id=" + idMessage.getId())
                .toString();

        MySqlExecuteHelper.execute(vertx, insertBuilder, mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                message.reply(COMMON_SUCCESS_MESSAGE);
            } else {
                message.reply(COMMON_FAILED_MESSAGE);
            }
        });
    }
}