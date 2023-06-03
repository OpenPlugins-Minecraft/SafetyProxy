package me.indian.safetyproxy;

/**
 * The MessageService interface provides methods for publishing messages and adding message listeners.
 */
public interface MessageService {

    /**
     * The default subject for published messages.
     */
    String SUBJECT = "SafetyProxy@Sync";

    /**
     * Publishes a message with the specified object and subject.
     *
     * @param object  The object to be published as a message.
     * @param subject The subject of the message.
     */
    void publishMessage(final Object object, final String subject);

    /**
     * Adds a message listener to receive messages of type T.
     *
     * @param listener The message listener to be added.
     * @param <T>      The type of messages the listener should receive.
     */
    <T> void addMessageListener(final AbstractMessageListener<T> listener);
}