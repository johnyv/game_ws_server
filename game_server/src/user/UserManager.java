package user;

import io.netty.channel.group.ChannelGroup;

import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private final ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();

    public UserManager(){}

    public void createGroup(){}
}
