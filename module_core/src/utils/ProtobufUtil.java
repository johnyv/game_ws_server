package utils;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class ProtobufUtil {
    public static ByteBuf getBytes(AbstractMessageLite<?, ?> proto) {
        byte[] bytes = proto.toByteArray();
        return Unpooled.copiedBuffer(bytes);
    }

    public static BinaryWebSocketFrame getFrame(MessageLite proto) {
        byte[] bytes = proto.toByteArray();
        ByteBuf buf = Unpooled.copiedBuffer(bytes);
        return new BinaryWebSocketFrame(buf);
    }

    public static byte[] packProto(int code, MessageLite proto) {
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
