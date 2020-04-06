package com.aries.crawler.trans.message;

import com.aries.crawler.trans.Messagable;

import java.math.BigInteger;

/**
 * immutable message, 用户数据message
 *
 * @author arowana
 */
public record SimpleInt64Message(BigInteger id) implements Messagable {
}
