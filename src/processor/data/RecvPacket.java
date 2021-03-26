package processor.data;

public class RecvPacket {
    private int length;
    private int protocol;
    private byte[] data;

    public RecvPacket(int length, int protocol, byte[] data) {
        this.length = length;
        this.protocol = protocol;
        this.data = data;
    }

    public int getProtocol() {
        return protocol;
    }

    public int getLength() {
        return length;
    }

    public byte[] getData() {
        return data;
    }
}
