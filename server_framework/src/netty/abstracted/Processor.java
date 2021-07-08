package netty.abstracted;

import netty.session.Session;
import netty.packet.ProtoMsg;

public abstract class Processor {
    protected int code = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    protected abstract void init(Session session, ProtoMsg protoMsg) throws Exception;

    public abstract void process(Session session, ProtoMsg protoMsg) throws Exception;

//    void send(String msg);
}