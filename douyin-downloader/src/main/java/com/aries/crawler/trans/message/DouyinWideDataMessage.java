package com.aries.crawler.trans.message;

import com.aries.crawler.model.douyincrawler.DouyinCrawlerLogModel;
import com.aries.crawler.trans.Messagable;

import java.math.BigInteger;

/**
 * immutable message, 宽表数据message
 *
 * @author arowana
 */
public record DouyinWideDataMessage(BigInteger id, Long awemeId, String awemeDesc, Long awemeCreateTime,
                                    Long authorUid, Long authorShortId, String authorNickname, String authorSignature,
                                    String avatarLargerUrl, String authorShareInfoQrcodeUrl,
                                    String videoCoverUrl, String videoDynamicCoverUrl, String videoDownloadAddrUrl,
                                    String videoShareUrl, String videoVideoTag, Long videoDuration,
                                    Integer type, Integer status, String ct, String ut) implements Messagable {

    public static DouyinWideDataMessage of(DouyinCrawlerLogModel douyinCrawlerLogModel) {
        return new DouyinWideDataMessageBuilder()
                .id(douyinCrawlerLogModel.getId())
                .awemeId(douyinCrawlerLogModel.getAwemeId())
                .awemeDesc(douyinCrawlerLogModel.getAwemeDesc())
                .awemeCreateTime(douyinCrawlerLogModel.getAwemeCreateTime())
                .authorUid(douyinCrawlerLogModel.getAuthorUid())
                .authorShortId(douyinCrawlerLogModel.getAuthorShortId())
                .authorNickname(douyinCrawlerLogModel.getAuthorNickname())
                .authorSignature(douyinCrawlerLogModel.getAuthorSignature())
                .avatarLargerUrl(douyinCrawlerLogModel.getAvatarLargerUrl())
                .authorShareInfoQrcodeUrl(douyinCrawlerLogModel.getAuthorShareInfoQrcodeUrl())
                .videoCoverUrl(douyinCrawlerLogModel.getVideoCoverUrl())
                .videoDynamicCoverUrl(douyinCrawlerLogModel.getVideoDynamicCoverUrl())
                .videoDownloadAddrUrl(douyinCrawlerLogModel.getVideoDownloadAddrUrl())
                .videoShareUrl(douyinCrawlerLogModel.getVideoShareUrl())
                .videoVideoTag(douyinCrawlerLogModel.getVideoVideoTag())
                .videoDuration(douyinCrawlerLogModel.getVideoDuration())
                .type(douyinCrawlerLogModel.getType())
                .status(douyinCrawlerLogModel.getStatus())
                .ct(douyinCrawlerLogModel.getCt())
                .ut(douyinCrawlerLogModel.getUt())
                .build();
    }

    public static final class DouyinWideDataMessageBuilder {
        private BigInteger id;
        private Long awemeId;
        private String awemeDesc;
        private Long awemeCreateTime;
        private Long authorUid;
        private Long authorShortId;
        private String authorNickname;
        private String authorSignature;
        private String avatarLargerUrl;
        private String authorShareInfoQrcodeUrl;
        private String videoCoverUrl;
        private String videoDynamicCoverUrl;
        private String videoDownloadAddrUrl;
        private String videoShareUrl;
        private String videoVideoTag;
        private Long videoDuration;
        private Integer type;
        private Integer status;
        private String ct;
        private String ut;

        private DouyinWideDataMessageBuilder() {
        }

        public static DouyinWideDataMessageBuilder aDouyinWideDataMessage() {
            return new DouyinWideDataMessageBuilder();
        }

        public DouyinWideDataMessageBuilder id(BigInteger id) {
            this.id = id;
            return this;
        }

        public DouyinWideDataMessageBuilder awemeId(Long awemeId) {
            this.awemeId = awemeId;
            return this;
        }

        public DouyinWideDataMessageBuilder awemeDesc(String awemeDesc) {
            this.awemeDesc = awemeDesc;
            return this;
        }

        public DouyinWideDataMessageBuilder awemeCreateTime(Long awemeCreateTime) {
            this.awemeCreateTime = awemeCreateTime;
            return this;
        }

        public DouyinWideDataMessageBuilder authorUid(Long authorUid) {
            this.authorUid = authorUid;
            return this;
        }

        public DouyinWideDataMessageBuilder authorShortId(Long authorShortId) {
            this.authorShortId = authorShortId;
            return this;
        }

        public DouyinWideDataMessageBuilder authorNickname(String authorNickname) {
            this.authorNickname = authorNickname;
            return this;
        }

        public DouyinWideDataMessageBuilder authorSignature(String authorSignature) {
            this.authorSignature = authorSignature;
            return this;
        }

        public DouyinWideDataMessageBuilder avatarLargerUrl(String avatarLargerUrl) {
            this.avatarLargerUrl = avatarLargerUrl;
            return this;
        }

        public DouyinWideDataMessageBuilder authorShareInfoQrcodeUrl(String authorShareInfoQrcodeUrl) {
            this.authorShareInfoQrcodeUrl = authorShareInfoQrcodeUrl;
            return this;
        }

        public DouyinWideDataMessageBuilder videoCoverUrl(String videoCoverUrl) {
            this.videoCoverUrl = videoCoverUrl;
            return this;
        }

        public DouyinWideDataMessageBuilder videoDynamicCoverUrl(String videoDynamicCoverUrl) {
            this.videoDynamicCoverUrl = videoDynamicCoverUrl;
            return this;
        }

        public DouyinWideDataMessageBuilder videoDownloadAddrUrl(String videoDownloadAddrUrl) {
            this.videoDownloadAddrUrl = videoDownloadAddrUrl;
            return this;
        }

        public DouyinWideDataMessageBuilder videoShareUrl(String videoShareUrl) {
            this.videoShareUrl = videoShareUrl;
            return this;
        }

        public DouyinWideDataMessageBuilder videoVideoTag(String videoVideoTag) {
            this.videoVideoTag = videoVideoTag;
            return this;
        }

        public DouyinWideDataMessageBuilder videoDuration(Long videoDuration) {
            this.videoDuration = videoDuration;
            return this;
        }

        public DouyinWideDataMessageBuilder type(Integer type) {
            this.type = type;
            return this;
        }

        public DouyinWideDataMessageBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public DouyinWideDataMessageBuilder ct(String ct) {
            this.ct = ct;
            return this;
        }

        public DouyinWideDataMessageBuilder ut(String ut) {
            this.ut = ut;
            return this;
        }

        public DouyinWideDataMessage build() {
            return new DouyinWideDataMessage(id, awemeId, awemeDesc, awemeCreateTime, authorUid, authorShortId, authorNickname, authorSignature, avatarLargerUrl, authorShareInfoQrcodeUrl, videoCoverUrl, videoDynamicCoverUrl, videoDownloadAddrUrl, videoShareUrl, videoVideoTag, videoDuration, type, status, ct, ut);
        }
    }
}
