package me.indian.safetyproxy;

public interface MessageService {

    String SUBJECT = "SafetyProxy@Sync";

    void publishMessage(final Object object, final String subject);

    void startListening();
}