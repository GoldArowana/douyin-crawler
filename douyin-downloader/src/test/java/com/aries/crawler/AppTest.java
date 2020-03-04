package com.aries.crawler;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() {
        Vertx vertx = Vertx.vertx();

//        HttpClient client = vertx.createHttpClient();
//        client.getNow("v9-dy-z.ixigua.com", "/ced073a2be8a2051e43a6f44d1a9f229/5e5d48bb/video/tos/cn/tos-cn-ve-15/78b8080fe66949899e020ede57832b86/?a=1128&br=0&bt=719&cr=0&cs=0&dr=0&ds=3&er=&l=202003030055560100140472051C5FC589&lr=aweme&qs=0&rc=anBtd2Z1aDc2czMzNmkzM0ApM2U6NDs1ZjxkN2lkZzpoOmdjX3NwLm1mMmFfLS0zLS9zcy5fNGFgXy0uLV9eLjNhYmM6Yw%3D%3D&vl=&vr=", response -> {
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

        Future<Void> fut1 = Future.future(promise -> vertx.fileSystem().createFile("target/classes/foo", promise));

        Future<Void> fut2 = fut1.compose(x -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束");
            return Future.<Void>future(promise -> vertx.fileSystem().createFile("target/classes/foo2", promise));
        });

//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
