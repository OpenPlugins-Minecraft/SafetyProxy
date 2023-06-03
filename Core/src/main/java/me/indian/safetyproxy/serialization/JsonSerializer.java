package me.indian.safetyproxy.serialization;

import me.indian.safetyproxy.helper.GsonHelper;

/**
 * The JsonSerializer class provides a utility method for serializing Java objects into JSON format.
 */
public final class JsonSerializer {

    /**
     * Serializes an object into its JSON representation.
     *
     * @param object The object to be serialized.
     * @return A JSON string representing the serialized object.
     * @throws NullPointerException if the object parameter is null.
     */
    public static String serialize(final Object object) {
        if (object == null) {
            throw new NullPointerException("object cannot be null");
        }
        return GsonHelper.getGson().toJson(object);
    }
}