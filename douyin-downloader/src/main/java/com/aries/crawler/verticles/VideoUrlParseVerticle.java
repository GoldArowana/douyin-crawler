package com.aries.crawler.verticles;

import com.aries.crawler.tools.MySqlExecuteHelper;
import com.aries.crawler.trans.message.DouyinVideoInfoMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import static com.aries.crawler.trans.EventBusTopic.LOGIC_DOUYIN_VIDEO_URL_PARSE;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_FAILED_MESSAGE;
import static com.aries.crawler.trans.message.CommonResponseMessage.COMMON_SUCCESS_MESSAGE;

public class VideoUrlParseVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(VideoInsertVerticle.class);


    @Override
    public void start() {
        // 用于插入视频数据
        vertx.eventBus().consumer(LOGIC_DOUYIN_VIDEO_URL_PARSE.getTopic(), this::mysqlDouyinVideoInsertHandler).setMaxBufferedMessages(4000);
    }

    private void mysqlDouyinVideoInsertHandler(Message<Object> message) {
        var videoInfoMessage = (DouyinVideoInfoMessage) message.body();
        HttpClient client = vertx.createHttpClient();

        var videoShareUrl = videoInfoMessage.videoShareUrl();
        var index = videoShareUrl.indexOf("/");
        var host = videoShareUrl.substring(0, index);
        var uri = videoShareUrl.substring(index);
        System.out.println(host);
        System.out.println(uri);
//        client.get(new RequestOptions()
//                        .setHost("www.iesdouyin.com")
//                        .setURI("/share/video/6772821096413580548/?region=CN&mid=6772787703437069070&u_code=19h7agc1k&titleType=title")
//                        .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
//                , response -> {
//                    response.bodyHandler(body -> {
//                        var bodyStr = new String(body.getBytes());
//                        var addrUrl = getPlayAddrUrl(bodyStr);
//                        System.out.println(addrUrl);
//                    });
//                }).setFollowRedirects(true).end();
//
//        MySqlExecuteHelper.prepareExecute(vertx, insertBuilder.getSql(), insertBuilder.getValues(), mysqlExecutorRes -> {
//            if (mysqlExecutorRes.succeeded()) {
//                logger.info("insert video succ, awemeid:" + videoInfoMessage.getAwemeId());
//                message.reply(COMMON_SUCCESS_MESSAGE);
//            } else {
//                logger.info("insert video. failed:" + mysqlExecutorRes.cause());
//                message.reply(COMMON_FAILED_MESSAGE);
//            }
//        });
    }

}
