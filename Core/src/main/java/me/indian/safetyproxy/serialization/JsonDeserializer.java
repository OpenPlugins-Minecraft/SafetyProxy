package me.indian.safetyproxy.serialization;

import me.indian.safetyproxy.helper.GsonHelper;

import java.nio.charset.StandardCharsets;

public final class JsonDeserializer {

    public static <T> T deserialize(final byte[] data, final Class<T> clazz) {
        final String dataReceived = new String(data, StandardCharsets.UTF_8);
        return deserialize(dataReceived, clazz);
    }

    public static <T> T deserialize(final String json, final Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            throw new NullPointerException("Json cannot be null or empty");
        }
        return GsonHelper.getGson().fromJson(json, clazz);
    }
}