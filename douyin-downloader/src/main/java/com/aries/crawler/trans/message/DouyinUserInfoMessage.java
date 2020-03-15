package com.aries.crawler.trans.message;

import com.aries.crawler.tools.Urls;
import com.aries.crawler.trans.Messagable;

/**
 * immutable message, 用户数据message
 *
 * @author arowana
 */
public class DouyinUserInfoMessage implements Messagable {
    private final Long uid;
    private final Long shortId;
    private final String nickname;
    private final String signature;
    private final String avatarLargerUrl;
    private final String shareUrl;
    private final String shareInfoQrCodeUrl;

    public DouyinUserInfoMessage(Long uid, Long shortId, String nickname, String signature, String avatarLargerUrl, String shareUrl, String shareInfoQrCodeUrl) {
        this.uid = uid;
        this.shortId = shortId;
        this.nickname = nickname;
        this.signature = signature;
        this.avatarLargerUrl = avatarLargerUrl;
        this.shareUrl = shareUrl;
        this.shareInfoQrCodeUrl = shareInfoQrCodeUrl;
    }

    public static DouyinUserInfoMessage of(DouyinWideDataMessage wideDataMessage) {
        return new DouyinUserInfoMessageBuilder()
                .uid(wideDataMessage.getAuthorUid())
                .shortId(wideDataMessage.getAuthorShortId())
                .nickname(wideDataMessage.getAuthorNickname())
                .signature(wideDataMessage.getAuthorSignature())
                .avatarLargerUrl(wideDataMessage.getAvatarLargerUrl())
                .shareUrl(Urls.getUserSharePage(wideDataMessage.getAuthorUid()))
                .shareInfoQrCodeUrl(wideDataMessage.getAuthorShareInfoQrcodeUrl())
                .build();
    }

    public Long getUid() {
        return uid;
    }

    public Long getShortId() {
        return shortId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSignature() {
        return signature;
    }

    public String getAvatarLargerUrl() {
        return avatarLargerUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public String getShareInfoQrCodeUrl() {
        return shareInfoQrCodeUrl;
    }

    @Override
    public String toString() {
        return "DouyinUserInfoMessage{" +
                "uid=" + uid +
                ", shortId=" + shortId +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", avatarLargerUrl='" + avatarLargerUrl + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", shareInfoQrCodeUrl='" + shareInfoQrCodeUrl + '\'' +
                '}';
    }

    public static final class DouyinUserInfoMessageBuilder {
        private Long uid;
        private Long shortId;
        private String nickname;
        private String signature;
        private String avatarLargerUrl;
        private String shareUrl;
        private String shareInfoQrCodeUrl;

        private DouyinUserInfoMessageBuilder() {
        }

        public static DouyinUserInfoMessageBuilder aDouyinUserInfoMessage() {
            return new DouyinUserInfoMessageBuilder();
        }

        public DouyinUserInfoMessageBuilder uid(Long uid) {
            this.uid = uid;
            return this;
        }

        public DouyinUserInfoMessageBuilder shortId(Long shortId) {
            this.shortId = shortId;
            return this;
        }

        public DouyinUserInfoMessageBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public DouyinUserInfoMessageBuilder signature(String signature) {
            this.signature = signature;
            return this;
        }

        public DouyinUserInfoMessageBuilder avatarLargerUrl(String avatarLargerUrl) {
            this.avatarLargerUrl = avatarLargerUrl;
            return this;
        }

        public DouyinUserInfoMessageBuilder shareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
            return this;
        }

        public DouyinUserInfoMessageBuilder shareInfoQrCodeUrl(String shareInfoQrCodeUrl) {
            this.shareInfoQrCodeUrl = shareInfoQrCodeUrl;
            return this;
        }

        public DouyinUserInfoMessage build() {
            return new DouyinUserInfoMessage(uid, shortId, nickname, signature, avatarLargerUrl, shareUrl, shareInfoQrCodeUrl);
        }
    }
}
