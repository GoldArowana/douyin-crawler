package com.aries.crawler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.RequestOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpParserTestVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HttpParserTestVerticle());
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();
        client.get(new RequestOptions()

                        .setHost("www.iesdouyin.com")
                        .setURI("/share/video/6772821096413580548/?region=CN&mid=6772787703437069070&u_code=19h7agc1k&titleType=title")
                        .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                , response -> {
                    response.bodyHandler(body -> {
                        var bodyStr = new String(body.getBytes());
                        var addrUrl = getPlayAddrUrl(bodyStr);
                        System.out.println(addrUrl);
                    });
                }).setFollowRedirects(true).end();
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
