package com.aries.crawler.model.douyincrawler;

import com.aries.crawler.annotation.MysqlField;
import com.aries.crawler.model.DataModelable;

import java.time.LocalDateTime;

/**
 * 抖音数据宽表
 *
 * @author arowana
 */
public class DouyinCrawlerLogModel implements DataModelable {
    /**
     * 提取用户数据完成
     */
    public static final Integer STATUS_USER_DONE = 1;

    /**
     * 提取视频信息完成
     */
    public static final Integer STATUS_VIDEO_DONE = 2;

    /**
     * 用户信息、视频信息 都提取完成
     */
    public static final Integer STATUS_ALL_DONE = 3;

    public static final String TABLE = "douyin_crawler_log";

    @MysqlField(alias = "id", type = Long.class)
    private Long id;

    @MysqlField(alias = "aweme_id", type = Long.class)
    public Long awemeId;

    @MysqlField(alias = "aweme_desc", type = String.class)
    public String awemeDesc;

    @MysqlField(alias = "aweme_create_time", type = Long.class)
    public Long awemeCreateTime;

    @MysqlField(alias = "author_uid", type = Long.class)
    public Long authorUid;

    @MysqlField(alias = "author_short_id", type = Long.class)
    public Long authorShortId;

    @MysqlField(alias = "author_nickname", type = String.class)
    public String authorNickname;

    @MysqlField(alias = "author_signature", type = String.class)
    public String authorSignature;

    @MysqlField(alias = "avatar_larger_url", type = String.class)
    public String avatarLargerUrl;

    @MysqlField(alias = "author_share_info_qrcode_url", type = String.class)
    public String authorShareInfoQrcodeUrl;

    @MysqlField(alias = "video_cover_url", type = String.class)
    public String videoCoverUrl;

    @MysqlField(alias = "video_dynamic_cover_url", type = String.class)
    public String videoDynamicCoverUrl;

    @MysqlField(alias = "video_download_addr_url", type = String.class)
    public String videoDownloadAddrUrl;

    @MysqlField(alias = "video_share_url", type = String.class)
    public String videoShareUrl;

    @MysqlField(alias = "video_tag", type = String.class)
    public String videoVideoTag;

    @MysqlField(alias = "video_duration", type = Long.class)
    public Long videoDuration;

    @MysqlField(alias = "type", type = Integer.class)
    public Integer type;

    @MysqlField(alias = "status", type = Integer.class)
    public Integer status;

    @MysqlField(alias = "ct", type = LocalDateTime.class)
    public LocalDateTime ct;

    @MysqlField(alias = "ut", type = LocalDateTime.class)
    public LocalDateTime ut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAwemeId() {
        return awemeId;
    }

    public void setAwemeId(Long awemeId) {
        this.awemeId = awemeId;
    }

    public String getAwemeDesc() {
        return awemeDesc;
    }

    public void setAwemeDesc(String awemeDesc) {
        this.awemeDesc = awemeDesc;
    }

    public Long getAwemeCreateTime() {
        return awemeCreateTime;
    }

    public void setAwemeCreateTime(Long awemeCreateTime) {
        this.awemeCreateTime = awemeCreateTime;
    }

    public Long getAuthorUid() {
        return authorUid;
    }

    public void setAuthorUid(Long authorUid) {
        this.authorUid = authorUid;
    }

    public Long getAuthorShortId() {
        return authorShortId;
    }

    public void setAuthorShortId(Long authorShortId) {
        this.authorShortId = authorShortId;
    }

    public String getAuthorNickname() {
        return authorNickname;
    }

    public void setAuthorNickname(String authorNickname) {
        this.authorNickname = authorNickname;
    }

    public String getAuthorSignature() {
        return authorSignature;
    }

    public void setAuthorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
    }

    public String getAvatarLargerUrl() {
        return avatarLargerUrl;
    }

    public void setAvatarLargerUrl(String avatarLargerUrl) {
        this.avatarLargerUrl = avatarLargerUrl;
    }

    public String getAuthorShareInfoQrcodeUrl() {
        return authorShareInfoQrcodeUrl;
    }

    public void setAuthorShareInfoQrcodeUrl(String authorShareInfoQrcodeUrl) {
        this.authorShareInfoQrcodeUrl = authorShareInfoQrcodeUrl;
    }

    public String getVideoCoverUrl() {
        return videoCoverUrl;
    }

    public void setVideoCoverUrl(String videoCoverUrl) {
        this.videoCoverUrl = videoCoverUrl;
    }

    public String getVideoDynamicCoverUrl() {
        return videoDynamicCoverUrl;
    }

    public void setVideoDynamicCoverUrl(String videoDynamicCoverUrl) {
        this.videoDynamicCoverUrl = videoDynamicCoverUrl;
    }

    public String getVideoDownloadAddrUrl() {
        return videoDownloadAddrUrl;
    }

    public void setVideoDownloadAddrUrl(String videoDownloadAddrUrl) {
        this.videoDownloadAddrUrl = videoDownloadAddrUrl;
    }

    public String getVideoShareUrl() {
        return videoShareUrl;
    }

    public void setVideoShareUrl(String videoShareUrl) {
        this.videoShareUrl = videoShareUrl;
    }

    public String getVideoVideoTag() {
        return videoVideoTag;
    }

    public void setVideoVideoTag(String videoVideoTag) {
        this.videoVideoTag = videoVideoTag;
    }

    public Long getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Long videoDuration) {
        this.videoDuration = videoDuration;
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

    public LocalDateTime getCt() {
        return ct;
    }

    public void setCt(LocalDateTime ct) {
        this.ct = ct;
    }

    public LocalDateTime getUt() {
        return ut;
    }

    public void setUt(LocalDateTime ut) {
        this.ut = ut;
    }

    @Override
    public String toString() {
        return "DouYinCrawlerLogModel{" +
                "id=" + id +
                ", awemeId=" + awemeId +
                ", awemeDesc='" + awemeDesc + '\'' +
                ", awemeCreateTime=" + awemeCreateTime +
                ", authorUid=" + authorUid +
                ", authorShortId=" + authorShortId +
                ", authorNickname='" + authorNickname + '\'' +
                ", authorSignature='" + authorSignature + '\'' +
                ", avatarLargerUrl='" + avatarLargerUrl + '\'' +
                ", authorShareInfoQrcodeUrl='" + authorShareInfoQrcodeUrl + '\'' +
                ", videoCoverUrl='" + videoCoverUrl + '\'' +
                ", videoDynamicCoverUrl='" + videoDynamicCoverUrl + '\'' +
                ", videoDownloadAddrUrl='" + videoDownloadAddrUrl + '\'' +
                ", videoShareUrl='" + videoShareUrl + '\'' +
                ", videoVideoTag='" + videoVideoTag + '\'' +
                ", videoDuration=" + videoDuration +
                ", type=" + type +
                ", status=" + status +
                ", ct=" + ct +
                ", ut=" + ut +
                '}';
    }
}
