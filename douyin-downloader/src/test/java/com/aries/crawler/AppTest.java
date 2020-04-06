package com.aries.crawler;

import com.aries.crawler.sqlbuilder.InsertBuilder;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.RequestOptions;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.aries.crawler.verticles.UpdateDataVerticle.getDateTimeAsString;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() {
        Vertx vertx = Vertx.vertx();

        HttpClient client = vertx.createHttpClient();
//        client.getNow("aweme.snssdk.com", "/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc560cdca46fc197329a17cb02eef09b72493338e49045f75c3a6cd886d97de228c6e6a1f93d3b9a63a26a63e40654c6655e&line=0", response -> {
//            System.out.println("Received response with status code " + response.statusCode());
//            response.bodyHandler(x -> {
//                vertx.fileSystem().writeFile("target/classes/a.mp4", Buffer.buffer(x.getBytes()), result -> {
//                    if (result.succeeded()) {
//                        System.out.println("File written");
//                    } else {
//                        System.err.println("Oh oh ..." + result.cause());
//                    }
//                });
//            });
//        });

        client.get(new RequestOptions().setHost("aweme.snssdk.com").setURI("/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc560cdca46fc197329a17cb02eef09b72493338e49045f75c3a6cd886d97de228c6e6a1f93d3b9a63a26a63e40654c6655e&line=0").addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36"), response -> {
            for (Map.Entry<String, String> header : response.headers()) {
                System.out.println(header);
            }
            System.out.println("Received response with status code " + response.statusCode());
            response.bodyHandler(x -> {
                vertx.fileSystem().writeFile("target/classes/a.mp4", Buffer.buffer(x.getBytes()), result -> {
                    if (result.succeeded()) {
                        System.out.println("File written");
                    } else {
                        System.err.println("Oh oh ..." + result.cause());
                    }
                });
            });
        })
                .setFollowRedirects(true)
                .end();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void TestInsertSql() {
        InsertBuilder ib = new InsertBuilder("douyin_user_info")
                .set("uid", 123)
                .set("short_id", 234234)
                .set("nickname", "jinlong")
                .onDuplicateKeyUpdate("ut", getDateTimeAsString(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));
        String sql = ib.getSql();
        List<Object> values = ib.getValues();

        System.out.println(sql);
        System.out.println(values);
    }

}
