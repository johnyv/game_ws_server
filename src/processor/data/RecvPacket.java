package processor.data;

public class RecvPacket {
    private byte[] data;

    public RecvPacket() {
    }

    public RecvPacket(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}
