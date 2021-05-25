package packet;

import com.google.protobuf.MessageLite;
import utils.ByteUtil;

public class MsgPack {
    private int length;
    private int code;
    private byte[] data;

    public MsgPack() {
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public static MsgPack unPack(byte[] bytes) {
        MsgPack pack = new MsgPack();

        int i, index = 0;
        byte[] lengthBytes = new byte[4];
        for (i = 0; i < 4; i++) {
            lengthBytes[index++] = bytes[i];
        }

        pack.setLength(ByteUtil.bytes2Int(lengthBytes));

        byte[] codeBytes = new byte[4];
        index = 0;
        for (i = 4; i < 8; i++) {
            codeBytes[index++] = bytes[i];
        }

        pack.setCode(ByteUtil.bytes2Int(codeBytes));

        byte[] dataBytes = new byte[bytes.length - 8];
        index = 0;
        for (i = 8; i < bytes.length; i++) {
            dataBytes[index++] = bytes[i];
        }

        pack.setData(dataBytes);

        return pack;
    }

    public static byte[] pack(int code, MessageLite proto) {
        byte data[] = proto.toByteArray();
        int length = data.length + 8;
        byte[] bytes = new byte[length];
        byte[] lengthBytes = ByteUtil.int2Bytes(length);
        byte[] codeBytes = ByteUtil.int2Bytes(code);
        for (int i = 0; i < length; i++) {
            if (i < 4) {
                bytes[i] = lengthBytes[i];
            } else if (i < 8) {
                bytes[i] = codeBytes[i - 4];
            } else {
                bytes[i] = data[i - 8];
            }
        }
        return bytes;
    }
}
