package me.indian.safetyproxy.serialization;

import me.indian.safetyproxy.helper.GsonHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

/**
 * The JsonDeserializer class provides utility methods for deserializing JSON data into Java objects.
 */
public final class JsonDeserializer {

    /**
     * Deserializes a byte array representing JSON data into an instance of the specified class.
     *
     * @param data  The byte array representing JSON data.
     * @param clazz The class to which the JSON data should be deserialized.
     * @param <T>   The type of the class to which the JSON data should be deserialized.
     * @return An instance of the specified class representing the deserialized JSON data.
     */
    public static <T> T deserialize(final byte[] data, @NotNull final Class<T> clazz) {
        final String dataReceived = new String(data, StandardCharsets.UTF_8);
        return deserialize(dataReceived, clazz);
    }

    /**
     * Deserializes a JSON string into an instance of the specified class.
     *
     * @param json  The JSON string to be deserialized.
     * @param clazz The class to which the JSON string should be deserialized.
     * @param <T>   The type of the class to which the JSON string should be deserialized.
     * @return An instance of the specified class representing the deserialized JSON string.
     * @throws NullPointerException if the json parameter is null or empty.
     */
    public static <T> T deserialize(@NotNull final String json, @NotNull final Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            throw new NullPointerException("Json cannot be null or empty");
        }
        return GsonHelper.getGson().fromJson(json, clazz);
    }
}