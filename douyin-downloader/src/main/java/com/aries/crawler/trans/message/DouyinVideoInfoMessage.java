package com.aries.crawler.trans.message;

import com.aries.crawler.trans.Messagable;

/**
 * immutable message, 视频数据message
 *
 * @author arowana
 */
public class DouyinVideoInfoMessage implements Messagable {
    private final Long awemeId;
    private final Long authorUid;
    private final String awemeDesc;
    private final Long awemeCreateTime;
    private final String videoCoverUrl;
    private final String videoDynamicCoverUrl;
    private final String videoDownloadAddrUrl;
    private final String videoShareUrl;
    private final String videoVideoTag;
    private final Long videoDuration;
    private final Integer type;

    public DouyinVideoInfoMessage(Long awemeId, Long authorUid, String awemeDesc, Long awemeCreateTime, String videoCoverUrl, String videoDynamicCoverUrl, String videoDownloadAddrUrl, String videoShareUrl, String videoVideoTag, Long videoDuration, Integer type) {
        this.awemeId = awemeId;
        this.authorUid = authorUid;
        this.awemeDesc = awemeDesc;
        this.awemeCreateTime = awemeCreateTime;
        this.videoCoverUrl = videoCoverUrl;
        this.videoDynamicCoverUrl = videoDynamicCoverUrl;
        this.videoDownloadAddrUrl = videoDownloadAddrUrl;
        this.videoShareUrl = videoShareUrl;
        this.videoVideoTag = videoVideoTag;
        this.videoDuration = videoDuration;
        this.type = type;
    }

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

    public Long getAwemeId() {
        return awemeId;
    }

    public Long getAuthorUid() {
        return authorUid;
    }

    public String getAwemeDesc() {
        return awemeDesc;
    }

    public Long getAwemeCreateTime() {
        return awemeCreateTime;
    }

    public String getVideoCoverUrl() {
        return videoCoverUrl;
    }

    public String getVideoDynamicCoverUrl() {
        return videoDynamicCoverUrl;
    }

    public String getVideoDownloadAddrUrl() {
        return videoDownloadAddrUrl;
    }

    public String getVideoShareUrl() {
        return videoShareUrl;
    }

    public String getVideoVideoTag() {
        return videoVideoTag;
    }

    public Long getVideoDuration() {
        return videoDuration;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        return "DouyinVideoInfoMessage{" +
                "awemeId=" + awemeId +
                ", authorUid=" + authorUid +
                ", awemeDesc='" + awemeDesc + '\'' +
                ", awemeCreateTime=" + awemeCreateTime +
                ", videoCoverUrl='" + videoCoverUrl + '\'' +
                ", videoDynamicCoverUrl='" + videoDynamicCoverUrl + '\'' +
                ", videoDownloadAddrUrl='" + videoDownloadAddrUrl + '\'' +
                ", videoShareUrl='" + videoShareUrl + '\'' +
                ", videoVideoTag='" + videoVideoTag + '\'' +
                ", videoDuration=" + videoDuration +
                ", type=" + type +
                '}';
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
