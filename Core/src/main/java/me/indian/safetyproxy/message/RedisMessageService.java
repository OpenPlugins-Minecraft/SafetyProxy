package me.indian.safetyproxy.message;

import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.serialization.JsonSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisMessageService implements MessageService {

    private JedisPool jedisPool;
    private ExecutorService executorService;

    public RedisMessageService(final String host, final int port) {
        try {
            this.jedisPool = new JedisPool(host, port);
            this.executorService = Executors.newSingleThreadExecutor();
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void publishMessage(final Object object, final String subject) {
        this.executorService.execute(() -> {
            try (final Jedis jedis = this.jedisPool.getResource()) {
                jedis.publish(subject, JsonSerializer.serialize(object));
            }
        });
    }
}