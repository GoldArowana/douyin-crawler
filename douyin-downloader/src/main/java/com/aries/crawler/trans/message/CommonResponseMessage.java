package com.aries.crawler.trans.message;

import com.aries.crawler.trans.Messagable;

/**
 * immutable，通用message
 *
 * @author arowana
 */
public record CommonResponseMessage<T>(Integer code, T message) implements Messagable {
    public static final CommonResponseMessage<Object> COMMON_SUCCESS_MESSAGE = CommonResponseMessage.CommonResponseMessageBuilder
            .aCommonResponseMessage()
            .code(100)
            .message("success")
            .build();

    public static final CommonResponseMessage<Object> COMMON_FAILED_MESSAGE = CommonResponseMessage.CommonResponseMessageBuilder
            .aCommonResponseMessage()
            .code(1000)
            .message("failed")
            .build();

    public static final class CommonResponseMessageBuilder<T> {
        private Integer code;
        private T message;

        private CommonResponseMessageBuilder() {
        }

        public static <T> CommonResponseMessageBuilder<T> aCommonResponseMessage() {
            return new CommonResponseMessageBuilder<T>();
        }

        public CommonResponseMessageBuilder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public CommonResponseMessageBuilder<T> message(T message) {
            this.message = message;
            return this;
        }

        public CommonResponseMessage<T> build() {
            return new CommonResponseMessage<>(code, message);
        }
    }
}
