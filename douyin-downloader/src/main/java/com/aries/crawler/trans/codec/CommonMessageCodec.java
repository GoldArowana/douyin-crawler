package com.aries.crawler.trans.codec;

import com.aries.crawler.trans.Messagable;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.ParameterizedType;
import java.util.UUID;

/**
 * common message codec（通用的codec）
 *
 * @author arowana
 */
public class CommonMessageCodec<T extends Messagable> implements MessageCodec<T, T> {
    @Override
    public void encodeToWire(Buffer buffer, T message) {
        // Easiest ways is using JSON object
        JsonObject jsonToEncode = JsonObject.mapFrom(message);

        // Encode object to string
        String jsonToStr = jsonToEncode.encode();

        // Length of JSON: is NOT characters count
        int length = jsonToStr.getBytes().length;

        // Write data into given buffer
        buffer.appendInt(length);
        buffer.appendString(jsonToStr);
    }

    @Override
    public T decodeFromWire(int position, Buffer buffer) {
        // My custom message starting from this *position* of buffer
        int _pos = position;

        // Length of JSON
        int length = buffer.getInt(_pos);

        // Get JSON string by it`s length
        // Jump 4 because getInt() == 4 bytes
        String jsonStr = buffer.getString(_pos += 4, _pos += length);
        JsonObject jsonObject = new JsonObject(jsonStr);

        // Get fields
        @SuppressWarnings("unchecked")
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        return jsonObject.mapTo(entityClass);
    }

    @Override
    public T transform(T commonMessage) {
        return commonMessage;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName() + UUID.randomUUID();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}