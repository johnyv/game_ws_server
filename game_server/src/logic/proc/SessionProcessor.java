package logic.proc;

import netty.abstracted.Processor;
import taurus.websocket.protobuf.ProtobufMsg;
import netty.session.Session;
import user.UserManager;

public class SessionProcessor extends Processor {
    @Override
    protected void init(Session session, ProtobufMsg protobufMsg) throws Exception {

    }

    @Override
    public void process(Session session, ProtobufMsg protobufMsg) throws Exception {
        init(session, protobufMsg);
        System.out.println("9999 ===>"+session.getId());
        UserManager.getInstance().removeUserBySessionId(session.getId());
        UserManager.getInstance().alluser();
    }
}
