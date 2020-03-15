package com.aries.crawler.trans.message;

import com.aries.crawler.trans.Messagable;

import java.math.BigInteger;

/**
 * immutable message, 用户数据message
 *
 * @author arowana
 */
public class SimpleInt64Message implements Messagable {
    private final BigInteger id;

    public SimpleInt64Message(BigInteger id) {
        this.id = id;
    }

    public BigInteger getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SimpleInt64Message{" +
                "id=" + id +
                '}';
    }
}
