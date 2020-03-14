package com.aries.crawler.sqlbuilder;

import com.aries.crawler.model.douyincrawler.DouyinCrawlerLogModel;
import org.junit.Assert;
import org.junit.Test;

public class TestSqlBuilder {
    @Test
    public void sqlSelectTest() {
        String sql = new SelectBuilder()
                .column("*")
                .from("douyin_crawler_log")
                .where("status != " + DouyinCrawlerLogModel.STATUS_ALL_DONE)
                .limit(10L)
                .toString();

        Assert.assertEquals(sql, "select * from douyin_crawler_log where status != 3 limit 10 offset 0");
    }
}
