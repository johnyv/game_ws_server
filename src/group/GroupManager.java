package group;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public enum GroupManager {
    INSTANCE;

    private final ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, ClientInfo> members = new ConcurrentHashMap<>();

    public void sendToAll(String groupName, Object obj) {
        if (groupMap.containsKey(groupName)) {
            ChannelGroup group = groupMap.get(groupName);
            group.flush();
            group.writeAndFlush(obj);
        }
    }

    public void create(String groupName) {
        if (groupMap.contains(groupName)) {
            return;
        }
        ChannelGroup cg = new DefaultChannelGroup(groupName, GlobalEventExecutor.INSTANCE);
        groupMap.put(groupName, cg);
    }

    public void remove() {

    }

    public void addMember(ClientInfo info) {
        if (members.contains(info.getChannelId())) {
            info.getChannel().writeAndFlush(new Object())
                    .addListener(new GenericFutureListener<Future<? super Void>>() {
                        @Override
                        public void operationComplete(Future<? super Void> future) throws Exception {
                            info.getChannel().closeFuture();
                        }
                    });
        }
        members.put(info.getChannelId(), info);
    }

    public void processAll() {
        for (Iterator iterator = members.values().iterator(); iterator.hasNext(); ) {
            ClientInfo info = (ClientInfo) iterator.next();
            if (info != null) {
                info.pollQuest();
            }
        }
    }
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
