package com.aries.crawler.verticles;

import com.aries.crawler.sqlbuilder.InsertBuilder;
import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.trans.message.DouyinVideoInfoMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.aries.crawler.trans.EventBusTopic.MYSQL_DOUYIN_VIDEO_INSERT;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_FAILED_MESSAGE;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_SUCCESS_MESSAGE;

/**
 * @author arowana
 */
public class VideoInsertVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(VideoInsertVerticle.class);


    @Override
    public void start() {
        // 用于插入视频数据
        vertx.eventBus().consumer(MYSQL_DOUYIN_VIDEO_INSERT.getTopic(), this::mysqlDouyinVideoInsertHandler).setMaxBufferedMessages(4000);
    }

    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    private void mysqlDouyinVideoInsertHandler(Message<Object> message) {
        var videoInfoMessage = (DouyinVideoInfoMessage) message.body();
        // 构建sql数据, 插入视频信息。
        var insertBuilder = new InsertBuilder("douyin_video_info")
                .set("id", videoInfoMessage.awemeId())
                .set("comments", videoInfoMessage.awemeDesc())
                .set("create_time", videoInfoMessage.awemeCreateTime())
                .set("uid", videoInfoMessage.authorUid())
                .set("cover_url", videoInfoMessage.videoCoverUrl())
                .set("dynamic_cover_url", videoInfoMessage.videoDynamicCoverUrl())
                .set("download_addr_url", videoInfoMessage.videoDownloadAddrUrl())
                .set("share_url", videoInfoMessage.videoShareUrl())
                .set("tag", videoInfoMessage.videoVideoTag())
                .set("duration", videoInfoMessage.videoDuration())
                .set("type", videoInfoMessage.type())
                .onDuplicateKeyUpdate("ut", getDateTimeAsString(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));
        logger.info("video sql:" + insertBuilder.getSql() + "---values:" + insertBuilder.getValues());

        MySqlExecuteHelper.prepareExecute(vertx, insertBuilder.getSql(), insertBuilder.getValues(), mysqlExecutorRes -> {
            if (mysqlExecutorRes.succeeded()) {
                logger.info("insert video succ, awemeid:" + videoInfoMessage.awemeId());
                message.reply(COMMON_SUCCESS_MESSAGE);
            } else {
                logger.info("insert video. failed:" + mysqlExecutorRes.cause());
                message.reply(COMMON_FAILED_MESSAGE);
            }
        });
    }

}
