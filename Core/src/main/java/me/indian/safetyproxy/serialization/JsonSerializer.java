package me.indian.safetyproxy.serialization;

import me.indian.safetyproxy.helper.GsonHelper;

public final class JsonSerializer {

    public static String serialize(final Object object) {
        if (object == null) {
            throw new NullPointerException("object cannot be null");
        }
        return GsonHelper.getGson().toJson(object);
    }
}