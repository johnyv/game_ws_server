package netty.abstracted;

import lombok.extern.slf4j.Slf4j;
import netty.session.Session;
import websocket.protobuf.ProtobufMsg;

@Slf4j
public abstract class Processor {
    protected int code = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    protected abstract void init(Session session, ProtobufMsg protobufMsg) throws Exception;

    public abstract void process(Session session, ProtobufMsg protobufMsg) throws Exception;

//    void send(String msg);
}
