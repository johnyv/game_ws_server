package session;

import service.processor.packet.SendPacket;

public interface ISession {
    public boolean isConnected();

    public void write(SendPacket packet);

    public void close();
}
