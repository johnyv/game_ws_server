package abstracted;

import session.Session;
import packet.ProtoMsg;

public abstract class Processor {
    protected int code = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    protected abstract void init(Session session, ProtoMsg packet) throws Exception;

    public abstract void process(Session session, ProtoMsg packet) throws Exception;

//    void send(String msg);
}
