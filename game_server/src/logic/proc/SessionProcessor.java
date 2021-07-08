package logic.proc;

import netty.abstracted.Processor;
import netty.packet.ProtoMsg;
import netty.session.Session;
import user.UserManager;

public class SessionProcessor extends Processor {
    @Override
    protected void init(Session session, ProtoMsg protoMsg) throws Exception {

    }

    @Override
    public void process(Session session, ProtoMsg protoMsg) throws Exception {
        init(session, protoMsg);
        System.out.println("9999 ===>"+session.getId());
        UserManager.getInstance().removeUserBySessionId(session.getId());
        UserManager.getInstance().alluser();
    }
}
