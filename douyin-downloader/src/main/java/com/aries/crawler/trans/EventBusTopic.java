package com.aries.crawler.trans;

/**
 * @author arowana
 */

public enum EventBusTopic {
    /**
     * 抖音用户数据插入
     */
    LOGIC_WIDEDATA_DISPATCH("logic.widedata.dispatch"),
    MYSQL_DOUYIN_USER_INSERT("mysql.douyin_user.insert"),
    MYSQL_DOUYIN_VIDEO_INSERT("mysql.douyin_video.insert"),
    MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_VIDEO("mysql.douyin_widedata.update.status.video"),
    MYSQL_DOUYIN_WIDEDATA_UPDATE_STATUS_USER("mysql.douyin_widedata.update.status.user");

    EventBusTopic(String topic) {
        this.topic = topic;
    }

    private String topic;

    public String getTopic() {
        return topic;
    }
}
