package com.aries.crawler.trans;

/**
 * @author arowana
 */

public enum EventBusTopic {
    /**
     * 抖音用户数据插入
     */
    LOGIC_DOUYIN_WIDEDATA_DISPATCH("logic.douyin.widedata.dispatch"),
    MYSQL_DOUYIN_USER_INSERT("mysql.douyin.user.insert"),
    MYSQL_DOUYIN_VIDEO_INSERT("mysql.douyin.video.insert"),
    MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_VIDEO("mysql.douyin.widedata.update.status.video"),
    MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_USER("mysql.douyin.widedata.update.status.user"),
    LOGIC_DOUYIN_VIDEO_DOWNLOAD("logic.douyin.video.url.parse"),
    MYSQL_DOUYIN_VIDEO__UPDATE_STATUS_DOWNLOADED("mysql.douyin.video.update.status.downloaded"),
    MYSQL_DOUYIN_VIDEO__UPDATE_STATUS_FAILED("mysql.douyin.video.update.status.failed");

    EventBusTopic(String topic) {
        this.topic = topic;
    }

    private String topic;

    public String getTopic() {
        return topic;
    }
}
