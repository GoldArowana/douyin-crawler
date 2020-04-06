package com.aries.crawler.model.douyincrawler;

import com.aries.crawler.annotation.MysqlField;
import com.aries.crawler.model.DataModelable;

import java.time.LocalDateTime;

/**
 * 抖音数据宽表
 *
 * @author arowana
 */
public class DouyinVideoModel implements DataModelable {
    /**
     * 默认值。未处理。
     */
    public static final Integer STATUS_VIDEO_DOWNLOAD_DEFAULT = 0;
    /**
     * 下载成功
     */
    public static final Integer STATUS_VIDEO_DOWNLOAD_SUCCESS = 1;

    /**
     * 下载失败
     */
    public static final Integer STATUS_VIDEO_DOWNLOAD_FAILED = 2;

    public static final String TABLE = "douyin_video_info";

    @MysqlField(alias = "id", type = Long.class)
    public Long id;

    @MysqlField(alias = "comments", type = String.class)
    public String comments;

    @MysqlField(alias = "create_time", type = Long.class)
    public Long createTime;

    @MysqlField(alias = "uid", type = Long.class)
    public Long uid;

    @MysqlField(alias = "cover_url", type = String.class)
    public String coverUrl;

    @MysqlField(alias = "dynamic_cover_url", type = String.class)
    public String dynamicCoverUrl;

    @MysqlField(alias = "download_addr_url", type = String.class)
    public String downloadAddrUrl;

    @MysqlField(alias = "share_url", type = String.class)
    public String shareUrl;

    @MysqlField(alias = "tag", type = String.class)
    public String tag;

    @MysqlField(alias = "duration", type = Long.class)
    public Long duration;

    @MysqlField(alias = "type", type = Integer.class)
    public Integer type;

    @MysqlField(alias = "status", type = Integer.class)
    public Integer status;

    @MysqlField(alias = "ct", type = LocalDateTime.class)
    public String ct;

    @MysqlField(alias = "ut", type = LocalDateTime.class)
    public String ut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDynamicCoverUrl() {
        return dynamicCoverUrl;
    }

    public void setDynamicCoverUrl(String dynamicCoverUrl) {
        this.dynamicCoverUrl = dynamicCoverUrl;
    }

    public String getDownloadAddrUrl() {
        return downloadAddrUrl;
    }

    public void setDownloadAddrUrl(String downloadAddrUrl) {
        this.downloadAddrUrl = downloadAddrUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut;
    }

    @Override
    public String toString() {
        return "DouyinVideoModel{" +
                "id=" + id +
                ", comments='" + comments + '\'' +
                ", createTime=" + createTime +
                ", uid=" + uid +
                ", coverUrl='" + coverUrl + '\'' +
                ", dynamicCoverUrl='" + dynamicCoverUrl + '\'' +
                ", downloadAddrUrl='" + downloadAddrUrl + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", tag='" + tag + '\'' +
                ", duration=" + duration +
                ", type=" + type +
                ", status=" + status +
                ", ct=" + ct +
                ", ut=" + ut +
                '}';
    }
}
