package me.indian.safetyproxy;

import org.jetbrains.annotations.NotNull;

/**
 * The AbstractMessageListener class is an abstract base class for implementing message listeners.
 *
 * @param <T> The type of messages that the listener handles.
 */
public abstract class AbstractMessageListener<T> {

    private final Class<T> clazz;
    private final String subject;

    /**
     * Constructs an AbstractMessageListener with the specified class type and subject.
     *
     * @param clazz   The class type of the messages that the listener handles.
     * @param subject The subject of the messages that the listener handles.
     */
    public AbstractMessageListener(@NotNull final Class<T> clazz, @NotNull final String subject) {
        this.clazz = clazz;
        this.subject = subject;
    }

    /**
     * Callback method invoked when a message of type T is received.
     *
     * @param type The message of type T received by the listener.
     */
    public abstract void onMessage(@NotNull final T type);

    /**
     * Returns the class type of the messages that the listener handles.
     *
     * @return The class type of the messages that the listener handles.
     */
    public Class<T> getClazz() {
        return this.clazz;
    }

    /**
     * Returns the subject of the messages that the listener handles.
     *
     * @return The subject of the messages that the listener handles.
     */
    public String getSubject() {
        return this.subject;
    }
}