package me.indian.safetyproxy.message;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.impl.NatsMessage;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.helper.GsonHelper;

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
        this.executorService.execute(() -> {
            final String json = GsonHelper.getGson().toJson(object);
            final Message message = NatsMessage.builder()
                    .subject(subject)
                    .data(json)
                    .build();
            this.connection.publish(message);
        });
    }
}