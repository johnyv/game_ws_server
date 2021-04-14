package service.processor;

import group.Client;
import service.processor.packet.RecvPacket;

public abstract class Processor {
    protected int code = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    protected abstract void init(Client client, RecvPacket packet) throws Exception;

    public abstract void process(Client client, RecvPacket packet) throws Exception;

//    void send(String msg);
}
