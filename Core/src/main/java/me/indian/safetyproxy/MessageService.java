package me.indian.safetyproxy;

public interface MessageService {

    void publishMessage(final String json, final String subject);
}