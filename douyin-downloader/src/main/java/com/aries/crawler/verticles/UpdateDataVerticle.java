package com.aries.crawler.verticles;

import com.aries.crawler.sqlbuilder.UpdateBuilder;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.trans.message.SimpleInt64Message;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.aries.crawler.trans.EventBusTopic.MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_USER;
import static com.aries.crawler.trans.EventBusTopic.MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_VIDEO;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_FAILED_MESSAGE;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_SUCCESS_MESSAGE;

/**
 * @author arowana
 */
public class UpdateDataVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(UpdateDataVerticle.class);

    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    @Override
    public void start() {
        // 更新款表的status状态为'已处理用户数据'状态
        vertx.eventBus().consumer(MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_USER.getTopic(), this::mysqlDouyinWideDataUpdateStatusUser).setMaxBufferedMessages(4000);
        // 更新款表的status状态为'已处理视频数据'状态
        vertx.eventBus().consumer(MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_VIDEO.getTopic(), this::mysqlDouyinWideDataUpdateStatusVideo).setMaxBufferedMessages(4000);
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