package com.aries.crawler.trans.message;

import com.aries.crawler.model.douyincrawler.DouyinVideoModel;
import com.aries.crawler.trans.Messagable;

/**
 * immutable message, 视频数据message
 *
 * @author arowana
 */
public record DouyinVideoInfoMessage(Long awemeId, Long authorUid, String awemeDesc, Long awemeCreateTime,
                                     String videoCoverUrl, String videoDynamicCoverUrl, String videoDownloadAddrUrl,
                                     String videoShareUrl, String videoVideoTag, Long videoDuration,
                                     Integer type) implements Messagable {

    public static DouyinVideoInfoMessage of(DouyinWideDataMessage wideDataMessage) {
        return new DouyinVideoInfoMessageBuilder()
                .awemeId(wideDataMessage.getAwemeId())
                .awemeDesc(wideDataMessage.getAwemeDesc())
                .awemeCreateTime(wideDataMessage.getAwemeCreateTime())
                .authorUid(wideDataMessage.getAuthorUid())
                .videoCoverUrl(wideDataMessage.getVideoCoverUrl())
                .videoDynamicCoverUrl(wideDataMessage.getVideoDynamicCoverUrl())
                .videoDownloadAddrUrl(wideDataMessage.getVideoDownloadAddrUrl())
                .videoShareUrl(wideDataMessage.getVideoShareUrl())
                .videoVideoTag(wideDataMessage.getVideoVideoTag())
                .videoDuration(wideDataMessage.getVideoDuration())
                .type(wideDataMessage.getType())
                .build();
    }

    public static DouyinVideoInfoMessage of(DouyinVideoModel douyinVideoModel) {
        return new DouyinVideoInfoMessageBuilder()
                .awemeId(douyinVideoModel.getId())
                .awemeDesc(douyinVideoModel.getComments())
                .awemeCreateTime(douyinVideoModel.getCreateTime())
                .authorUid(douyinVideoModel.getUid())
                .videoCoverUrl(douyinVideoModel.getCoverUrl())
                .videoDynamicCoverUrl(douyinVideoModel.getDynamicCoverUrl())
                .videoDownloadAddrUrl(douyinVideoModel.getDownloadAddrUrl())
                .videoShareUrl(douyinVideoModel.getShareUrl())
                .videoVideoTag(douyinVideoModel.getTag())
                .videoDuration(douyinVideoModel.getDuration())
                .type(douyinVideoModel.getType())
                .build();
    }

    public static final class DouyinVideoInfoMessageBuilder {
        private Long awemeId;
        private Long authorUid;
        private String awemeDesc;
        private Long awemeCreateTime;
        private String videoCoverUrl;
        private String videoDynamicCoverUrl;
        private String videoDownloadAddrUrl;
        private String videoShareUrl;
        private String videoVideoTag;
        private Long videoDuration;
        private Integer type;

        private DouyinVideoInfoMessageBuilder() {
        }

        public static DouyinVideoInfoMessageBuilder aDouyinVideoInfoMessage() {
            return new DouyinVideoInfoMessageBuilder();
        }

        public DouyinVideoInfoMessageBuilder awemeId(Long awemeId) {
            this.awemeId = awemeId;
            return this;
        }

        public DouyinVideoInfoMessageBuilder authorUid(Long authorUid) {
            this.authorUid = authorUid;
            return this;
        }

        public DouyinVideoInfoMessageBuilder awemeDesc(String awemeDesc) {
            this.awemeDesc = awemeDesc;
            return this;
        }

        public DouyinVideoInfoMessageBuilder awemeCreateTime(Long awemeCreateTime) {
            this.awemeCreateTime = awemeCreateTime;
            return this;
        }

        public DouyinVideoInfoMessageBuilder videoCoverUrl(String videoCoverUrl) {
            this.videoCoverUrl = videoCoverUrl;
            return this;
        }

        public DouyinVideoInfoMessageBuilder videoDynamicCoverUrl(String videoDynamicCoverUrl) {
            this.videoDynamicCoverUrl = videoDynamicCoverUrl;
            return this;
        }

        public DouyinVideoInfoMessageBuilder videoDownloadAddrUrl(String videoDownloadAddrUrl) {
            this.videoDownloadAddrUrl = videoDownloadAddrUrl;
            return this;
        }

        public DouyinVideoInfoMessageBuilder videoShareUrl(String videoShareUrl) {
            this.videoShareUrl = videoShareUrl;
            return this;
        }

        public DouyinVideoInfoMessageBuilder videoVideoTag(String videoVideoTag) {
            this.videoVideoTag = videoVideoTag;
            return this;
        }

        public DouyinVideoInfoMessageBuilder videoDuration(Long videoDuration) {
            this.videoDuration = videoDuration;
            return this;
        }

        public DouyinVideoInfoMessageBuilder type(Integer type) {
            this.type = type;
            return this;
        }

        public DouyinVideoInfoMessage build() {
            return new DouyinVideoInfoMessage(awemeId, authorUid, awemeDesc, awemeCreateTime, videoCoverUrl, videoDynamicCoverUrl, videoDownloadAddrUrl, videoShareUrl, videoVideoTag, videoDuration, type);
        }
    }
}
