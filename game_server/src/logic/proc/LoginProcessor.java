package logic.proc;

import abstracted.Processor;
import logic.protocol.LoginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import packet.ProtoMsg;
import session.Session;
import user.User;
import user.UserManager;

public class LoginProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(LoginProcessor.class);
    LoginInfo loginInfo;
    @Override
    protected void init(Session session, ProtoMsg protoMsg) throws Exception {
        loginInfo = LoginInfo.parseFrom(protoMsg.getData());
    }

    @Override
    public void process(Session session, ProtoMsg protoMsg) throws Exception {
        init(session, protoMsg);
        logger.info("login id:" + loginInfo.getId());

        User user = new User(loginInfo.getId(), loginInfo.getPwd(), session);
        UserManager.getInstance().addUser(user);

        LoginInfo.Builder builder = LoginInfo.newBuilder();
        builder.setId(loginInfo.getId());
        builder.setEnterTime(System.currentTimeMillis());
        LoginInfo info = builder.build();
        byte[] bytes0 = ProtoMsg.pack(1002, info);
        byte[] bytes1 = ProtoMsg.pack(2002, info);


        user.send(bytes0);
        UserManager.getInstance().sendToAll(bytes1);
        UserManager.getInstance().alluser();
    }
}
