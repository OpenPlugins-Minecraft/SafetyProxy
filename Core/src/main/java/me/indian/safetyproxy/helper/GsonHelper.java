package me.indian.safetyproxy.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonHelper {

    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    public static Gson getGson() {
        return gson;
    }
}