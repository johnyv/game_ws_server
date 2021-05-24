package packet;

import com.google.protobuf.MessageLite;
import utils.ByteUtil;

public class SendPacket {
    byte[] packetData;

    public SendPacket(int code, MessageLite proto) {
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
        packetData = bytes;
    }

    public byte[] getData() {
        return packetData;
    }
}
