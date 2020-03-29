package com.aries.crawler.trans.message;

import com.aries.crawler.tools.Urls;
import com.aries.crawler.trans.Messagable;

/**
 * immutable message, 用户数据message
 *
 * @author arowana
 */

public record DouyinUserInfoMessage(Long uid, Long shortId, String nickname, String signature,
                                    String avatarLargerUrl, String shareUrl,
                                    String shareInfoQrCodeUrl) implements Messagable {

    public static DouyinUserInfoMessage of(DouyinWideDataMessage wideDataMessage) {
        return new DouyinUserInfoMessageBuilder()
                .uid(wideDataMessage.authorUid())
                .shortId(wideDataMessage.authorShortId())
                .nickname(wideDataMessage.authorNickname())
                .signature(wideDataMessage.authorSignature())
                .avatarLargerUrl(wideDataMessage.avatarLargerUrl())
                .shareUrl(Urls.getUserSharePage(wideDataMessage.authorUid()))
                .shareInfoQrCodeUrl(wideDataMessage.authorShareInfoQrcodeUrl())
                .build();
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
