package me.indian.safetyproxy.communication;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.impl.NatsMessage;
import me.indian.safetyproxy.AbstractMessageListener;
import me.indian.safetyproxy.DataPacket;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.serialization.JsonDeserializer;
import me.indian.safetyproxy.serialization.JsonSerializer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NatsMessageService implements MessageService {

    private Connection connection;
    private ExecutorService executorService;

    public NatsMessageService(@NotNull final Options options) {
        try {
            this.connection = Nats.connect(options);
            this.executorService = Executors.newSingleThreadExecutor();
        } catch (final IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void publishMessage(@NotNull final Object object, @NotNull final String subject) {
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
    public <T> void addMessageListener(@NotNull final AbstractMessageListener<T> listener) {
        final Dispatcher dispatcher = this.connection.createDispatcher(message -> {
            final T dataReceived = JsonDeserializer.deserialize(message.getData(), listener.getClazz());
            listener.onMessage(dataReceived);
        });
        dispatcher.subscribe(MessageService.SUBJECT);
    }
}