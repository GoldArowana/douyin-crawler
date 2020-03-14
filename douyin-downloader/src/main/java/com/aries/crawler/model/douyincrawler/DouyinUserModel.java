package com.aries.crawler.model.douyincrawler;

import com.aries.crawler.annotation.MysqlField;
import com.aries.crawler.model.DataModelable;

import java.time.LocalDateTime;

/**
 * 抖音数据宽表
 *
 * @author arowana
 */
public class DouyinUserModel implements DataModelable {
    @MysqlField(alias = "uid", type = Long.class)
    public Long uid;

    @MysqlField(alias = "short_id", type = Long.class)
    public Long shortId;

    @MysqlField(alias = "nickname", type = String.class)
    public String nickname;

    @MysqlField(alias = "signature", type = String.class)
    public String signature;

    @MysqlField(alias = "avatar_larger_url", type = String.class)
    public String avatarLargerUrl;

    @MysqlField(alias = "share_url", type = String.class)
    public String shareUrl;

    @MysqlField(alias = "share_info_qrcode_url", type = String.class)
    public String shareInfoQrCodeUrl;

    @MysqlField(alias = "ct", type = LocalDateTime.class)
    public LocalDateTime ct;

    @MysqlField(alias = "ut", type = LocalDateTime.class)
    public LocalDateTime ut;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getShortId() {
        return shortId;
    }

    public void setShortId(Long shortId) {
        this.shortId = shortId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatarLargerUrl() {
        return avatarLargerUrl;
    }

    public void setAvatarLargerUrl(String avatarLargerUrl) {
        this.avatarLargerUrl = avatarLargerUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareInfoQrCodeUrl() {
        return shareInfoQrCodeUrl;
    }

    public void setShareInfoQrCodeUrl(String shareInfoQrCodeUrl) {
        this.shareInfoQrCodeUrl = shareInfoQrCodeUrl;
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
        return "DouyinUserModel{" +
                "uid=" + uid +
                ", shortId=" + shortId +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", avatarLargerUrl='" + avatarLargerUrl + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", shareInfoQrCodeUrl='" + shareInfoQrCodeUrl + '\'' +
                ", ct=" + ct +
                ", ut=" + ut +
                '}';
    }
}
