package service.processor.packet;

import utils.ByteUtil;

public class RecvPacket {
    private int length;
    private int code;
    private byte[] data;

    public RecvPacket(byte[] bytes) {

        int i, index = 0;
        byte[] lengthBytes = new byte[4];
        for (i = 0; i < 4; i++) {
            lengthBytes[index++] = bytes[i];
        }
        this.length = ByteUtil.bytes2Int(lengthBytes);

        byte[] codeBytes = new byte[4];
        index = 0;
        for (i = 4; i < 8; i++) {
            codeBytes[index++] = bytes[i];
        }
        this.code = ByteUtil.bytes2Int(codeBytes);

        byte[] dataBytes = new byte[bytes.length - 8];
        index = 0;
        for (i = 8; i < bytes.length; i++) {
            dataBytes[index++] = bytes[i];
        }

        this.data = dataBytes;

        System.out.println("length:" + length);
        System.out.println("code:" + code);
    }

    public int getCode() {
        return code;
    }

    public int getLength() {
        return length;
    }

    public byte[] getData() {
        return data;
    }
}
