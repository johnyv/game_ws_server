package logic.proc;

import netty.abstracted.Processor;
import logic.protocol.Player;
import logic.protocol.UserList;
import taurus.websocket.protobuf.ProtobufMsg;
import netty.session.Session;
import user.User;
import user.UserManager;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class UserListProcessor extends Processor {
    @Override
    protected void init(Session session, ProtobufMsg protobufMsg) throws Exception {

    }

    @Override
    public void process(Session session, ProtobufMsg protobufMsg) throws Exception {
        init(session, protobufMsg);

        UserList.Builder builder = UserList.newBuilder();
        ConcurrentHashMap<String, User> uList = UserManager.getInstance().getUserList();
        for (Iterator iterator = uList.values().iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();
            if (user != null) {
                Player.Builder p = Player.newBuilder();
                p.setId(user.getUid());
                p.setName(user.getName());
                p.setEnterTime(0);

                builder.addUserList(p);
            }
        }

        byte[] bytes = ProtobufMsg.pack(1004, builder.build());
        session.write(bytes);
    }
}
