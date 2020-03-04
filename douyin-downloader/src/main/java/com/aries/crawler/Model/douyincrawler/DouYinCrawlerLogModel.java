package com.aries.crawler.Model.douyincrawler;

import com.aries.crawler.Model.DataModel;
import com.aries.crawler.annotation.MysqlField;

/**
 * @author arowana
 */
public class DouYinCrawlerLogModel extends DataModel {
    @MysqlField(alias = "id", type = Long.class)
    private Long id;
    public Long ids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIds() {
        return ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }
}
