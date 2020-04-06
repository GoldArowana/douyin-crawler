package com.aries.crawler.verticles;

import com.aries.crawler.tools.Urls;
import com.aries.crawler.trans.message.DouyinVideoInfoMessage;
import com.aries.crawler.trans.message.SimpleInt64Message;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aries.crawler.trans.EventBusTopic.*;

public class VideoDownloadVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(VideoDownloadVerticle.class);
    private static final String MY_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.148 Safari/537.36";

    @Override
    public void start() {
        // 下载视频
        vertx.eventBus().consumer(LOGIC_DOUYIN_VIDEO_DOWNLOAD.getTopic(), this::mysqlDouyinVideoInsertHandler).setMaxBufferedMessages(4000);
    }

    private void mysqlDouyinVideoInsertHandler(Message<Object> message) {
        var videoInfoMessage = (DouyinVideoInfoMessage) message.body();
        HttpClient client = vertx.createHttpClient();

        var videoPageUrlInfo = Urls.getInfo(videoInfoMessage.videoShareUrl());
        System.out.println(videoInfoMessage.videoShareUrl());
        client.get(new RequestOptions().setHost(videoPageUrlInfo.host()).setURI(videoPageUrlInfo.path()).addHeader("user-agent", MY_UA), response -> {
            response.bodyHandler(body -> {
                var bodyStr = new String(body.getBytes());
                var addrUrl = getPlayAddrUrl(bodyStr);
                System.out.println(addrUrl);
                downloadMp4(addrUrl, "/Users/arowana/Movies/douyin/", videoInfoMessage.awemeId());
            });
        }).setFollowRedirects(true).end();
    }

    private void downloadMp4(String addrUrl, String filePath, Long awemeId) {
        vertx.executeBlocking(future -> {
            if (addrUrl == null || addrUrl.equals("")) {
                future.complete(200);
            } else {
                HttpClient client = vertx.createHttpClient();
                var videoDownloadUrlInfo = Urls.getInfo(addrUrl);
                client.get(new RequestOptions().setHost(videoDownloadUrlInfo.host()).setURI(videoDownloadUrlInfo.path()).addHeader("user-agent", MY_UA), httpResponse -> {
                    httpResponse.bodyHandler(httpBody -> {
                        final String mp4PathName = filePath + awemeId + ".mp4";
                        vertx.fileSystem().exists(mp4PathName, fileRes -> {
                            if (!fileRes.result()) {
                                vertx.fileSystem().writeFile(mp4PathName, Buffer.buffer(httpBody.getBytes()), fileStoreRes -> {
                                    if (fileStoreRes.succeeded()) {
                                        logger.info("file written. pathname:" + mp4PathName);
                                    } else {
                                        logger.info("file written failed. pathname: " + mp4PathName + ", cause: " + fileStoreRes.cause());
                                    }
                                });
                            }
                        });

                    });
                }).setFollowRedirects(true).end();
                future.complete(100);
            }
        }, res -> {
            if (res.succeeded() && res.result() instanceof Integer s) {
                switch (s) {
                    case 100 -> vertx.eventBus().request(MYSQL_DOUYIN_VIDEO__UPDATE_STATUS_DOWNLOADED.getTopic(), new SimpleInt64Message(BigInteger.valueOf(awemeId)), updateReply -> {
                        if (updateReply.succeeded()) {
                            logger.info("update status video downloaded succ ...");
                        } else {
                            logger.info("update status video downloaded fail ...");
                        }
                    });
                    case 200 -> vertx.eventBus().request(MYSQL_DOUYIN_VIDEO__UPDATE_STATUS_FAILED.getTopic(), new SimpleInt64Message(BigInteger.valueOf(awemeId)), updateReply -> {
                        if (updateReply.succeeded()) {
                            logger.info("update status video failed-status succ ...");
                        } else {
                            logger.info("update status video failed-status fail ...");
                        }
                    });
                }
            } else {
                logger.error(res);
            }
        });
    }

    public String getPlayAddrUrl(String body) {
        int indexOfPlayAddr = body.indexOf("playAddr: ");
        int indexOfCover = body.indexOf("cover: ");
        String subBody = body.substring(indexOfPlayAddr, indexOfCover);
        Pattern r = Pattern.compile("playAddr: \"(.*)\"");
        Matcher m = r.matcher(subBody);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }

}
