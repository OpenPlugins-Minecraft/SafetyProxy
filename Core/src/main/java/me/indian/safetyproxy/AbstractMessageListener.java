package me.indian.safetyproxy;

public abstract class AbstractMessageListener<T> {

    private final Class<T> clazz;
    private final String subject;

    public AbstractMessageListener(final Class<T> clazz, final String subject) {
        this.clazz = clazz;
        this.subject = subject;
    }

    public abstract void onMessage(final T type);

    public Class<T> getClazz() {
        return this.clazz;
    }

    public String getSubject() {
        return this.subject;
    }
}