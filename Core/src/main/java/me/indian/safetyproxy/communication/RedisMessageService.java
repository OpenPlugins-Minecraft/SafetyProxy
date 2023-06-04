package me.indian.safetyproxy.communication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.indian.safetyproxy.AbstractMessageListener;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.serialization.JsonDeserializer;
import me.indian.safetyproxy.serialization.JsonSerializer;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class RedisMessageService implements MessageService {

    private JedisPool jedisPool;
    private ExecutorService executorService;

    public RedisMessageService(@NotNull final String host, final int port /* TODO: support for password */) {
        try {
            this.jedisPool = new JedisPool(host, port);
            this.executorService = Executors.newSingleThreadExecutor();
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void publishMessage(@NotNull final Object object, @NotNull final String subject) {
        this.executorService.execute(() -> {
            try (final Jedis jedis = this.jedisPool.getResource()) {
                jedis.publish(subject, JsonSerializer.serialize(object));
            }
        });
    }

    @Override
    public <T> void addMessageListener(@NotNull final AbstractMessageListener<T> listener) {
        this.executorService.execute(() -> {
            try (final Jedis jedis = this.jedisPool.getResource()) {
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(final String channel, final String message) {
                        final T dataReceived = JsonDeserializer.deserialize(message, listener.getClazz());
                        listener.onMessage(dataReceived);
                    }
                }, MessageService.SUBJECT);
            }
        });
    }
}