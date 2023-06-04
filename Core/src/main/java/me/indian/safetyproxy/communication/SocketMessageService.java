package me.indian.safetyproxy.communication;

import me.indian.safetyproxy.AbstractMessageListener;
import me.indian.safetyproxy.DataPacket;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.serialization.JsonDeserializer;
import me.indian.safetyproxy.serialization.JsonSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@VisibleForTesting
public class SocketMessageService implements MessageService {

    private DatagramSocket socket;
    private ExecutorService executorService;

    public SocketMessageService() {
        try {
            this.socket = new DatagramSocket();
            this.executorService = Executors.newSingleThreadExecutor();
        } catch (final IOException exception) {
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
            final byte[] data = json.getBytes();
            try {
                final InetAddress address = InetAddress.getByName("localhost"); // TODO: receiver ip
                final int port = 12345; // TODO: receiver port
                final DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                this.socket.send(packet);
            } catch (final IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public <T> void addMessageListener(@NotNull final AbstractMessageListener<T> listener) {
        // TODO
        try {
            final byte[] buffer = new byte[1024];
            final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            this.socket.receive(packet);

            final String json = new String(packet.getData(), 0, packet.getLength());
            final T dataReceived = JsonDeserializer.deserialize(json, listener.getClazz());
            listener.onMessage(dataReceived);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
}