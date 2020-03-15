package com.aries.crawler.model.douyincrawler;

import com.aries.crawler.annotation.MysqlField;
import com.aries.crawler.model.DataModelable;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * 抖音数据宽表
 *
 * @author arowana
 */
public class DouyinVideoModel implements DataModelable {
    @MysqlField(alias = "id", type = BigInteger.class)
    public BigInteger id;

    @MysqlField(alias = "comments", type = String.class)
    public String comments;

    @MysqlField(alias = "create_time", type = BigInteger.class)
    public BigInteger createTime;

    @MysqlField(alias = "uid", type = BigInteger.class)
    public BigInteger uid;

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

    @MysqlField(alias = "duration", type = BigInteger.class)
    public BigInteger duration;
    @MysqlField(alias = "type", type = Integer.class)
    public Integer type;

    @MysqlField(alias = "status", type = Integer.class)
    public Integer status;

    @MysqlField(alias = "ct", type = LocalDateTime.class)
    public String ct;

    @MysqlField(alias = "ut", type = LocalDateTime.class)
    public String ut;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigInteger getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BigInteger createTime) {
        this.createTime = createTime;
    }

    public BigInteger getUid() {
        return uid;
    }

    public void setUid(BigInteger uid) {
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

    public BigInteger getDuration() {
        return duration;
    }

    public void setDuration(BigInteger duration) {
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
