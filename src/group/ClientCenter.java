package group;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public enum ClientCenter {
    INSTANCE;

    private final ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Client> members = new ConcurrentHashMap<>();

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

    public void addMember(Client info) {
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
            Client info = (Client) iterator.next();
            if (info != null) {
                info.pollQuest();
            }
        }
    }

    public enum Group {
        INSTANCE;
        private final Set<String> COMMON_GROUP = new HashSet<>();

        public void add(String groupName) {
            if (COMMON_GROUP.contains(groupName)) {
                return;
            }
            COMMON_GROUP.add(groupName);
        }

        public Set<String> getAllGroup() {
            Set<String> set = new HashSet<>();
            set.addAll(COMMON_GROUP);
            return set;
        }

        public Set<String> getCommonGroup() {
            return COMMON_GROUP;
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
