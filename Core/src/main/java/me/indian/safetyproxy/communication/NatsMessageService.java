package me.indian.safetyproxy.communication;

import io.nats.client.*;
import io.nats.client.impl.NatsMessage;
import me.indian.safetyproxy.AbstractMessageListener;
import me.indian.safetyproxy.DataPacket;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.serialization.JsonDeserializer;
import me.indian.safetyproxy.serialization.JsonSerializer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NatsMessageService implements MessageService {

    private Connection connection;
    private ExecutorService executorService;

    public NatsMessageService(final Options options) {
        try {
            this.connection = Nats.connect(options);
            this.executorService = Executors.newSingleThreadExecutor();
        } catch (final IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void publishMessage(final Object object, final String subject) {
        if (!object.getClass().isAssignableFrom(DataPacket.class)) {
            throw new UnsupportedOperationException("object must be of type DataPacket");
        }
        this.executorService.execute(() -> {
            final String json = JsonSerializer.serialize(object);
            final Message message = NatsMessage.builder()
                    .subject(subject)
                    .data(json)
                    .build();
            this.connection.publish(message);
        });
    }

    @Override
    public <T> void addMessageListener(final AbstractMessageListener<T> listener) {
        final Dispatcher dispatcher = this.connection.createDispatcher(message -> {
            final T dataReceived = JsonDeserializer.deserialize(message.getData(), listener.getClazz());
            listener.onMessage(dataReceived);
        });
        dispatcher.subscribe(MessageService.SUBJECT);
    }
}