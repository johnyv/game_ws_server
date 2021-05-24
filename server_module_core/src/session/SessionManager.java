package session;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final static SessionManager instance = new SessionManager();

    //    private final ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Session> sessionMap;// = new ConcurrentHashMap<String, Session>();

    public SessionManager() {
        sessionMap = new ConcurrentHashMap<String, Session>();
    }

    public static SessionManager getInstance() {
        return instance;
    }

//    public void sendToAll(String groupName, Object obj) {
//        if (groupMap.containsKey(groupName)) {
//            ChannelGroup group = groupMap.get(groupName);
//            group.flush();
//            group.writeAndFlush(obj);
//        }
//    }

//    public void create(String groupName) {
//        if (groupMap.contains(groupName)) {
//            return;
//        }
//        ChannelGroup cg = new DefaultChannelGroup(groupName, GlobalEventExecutor.INSTANCE);
//        groupMap.put(groupName, cg);
//    }

    public void remove() {

    }

    public void add(Session session) {
        if (sessionMap.contains(session.getChannelId())) {
            session.getChannel().writeAndFlush(new Object())
                    .addListener(new GenericFutureListener<Future<? super Void>>() {
                        @Override
                        public void operationComplete(Future<? super Void> future) throws Exception {
                            session.getChannel().closeFuture();
                        }
                    });
        }
        sessionMap.put(session.getChannelId(), session);
    }

    public void processAll() {
        for (Iterator iterator = sessionMap.values().iterator(); iterator.hasNext(); ) {
            Session info = (Session) iterator.next();
            if (info != null) {
                info.pollQuest();
            }
        }
    }

//    public enum Group {
//        INSTANCE;
//        private final Set<String> COMMON_GROUP = new HashSet<>();
//
//        public void add(String groupName) {
//            if (COMMON_GROUP.contains(groupName)) {
//                return;
//            }
//            COMMON_GROUP.add(groupName);
//        }
//
//        public Set<String> getAllGroup() {
//            Set<String> set = new HashSet<>();
//            set.addAll(COMMON_GROUP);
//            return set;
//        }
//
//        public Set<String> getCommonGroup() {
//            return COMMON_GROUP;
//        }
//    }
//    public void sendToAllMembers(Object obj) {
//        members.forEach((k, v) -> {
//            v.writeAndFlush(obj);
//        });
//    }
//
//    public boolean sendToMember(String id, Object obj) {
//        if (members.containsKey(id)) {
//            members.get(id).writeAndFlush(obj);
//            return true;
//        }
//        return false;
//    }
}
