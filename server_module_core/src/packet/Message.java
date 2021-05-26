package packet;

public class Message {
    private byte[] contents;

    private int code;

    public Message(int code, byte[] contents) {
        this.code = code;
        this.contents = contents;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
