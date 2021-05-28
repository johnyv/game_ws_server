package user;

import io.netty.channel.group.ChannelGroup;
import session.Session;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private final ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    private static UserManager instance = new UserManager();
    public static UserManager getInstance(){return instance;}

    public UserManager(){}

    public void addUser(User user){
        if (users.contains(user.getUid())){
            return;
        }
        users.put(user.getUid(), user);
    }

    public void sendToAll(byte[] bytes){
        for (Iterator iterator = users.values().iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();
            if (user != null) {
                System.out.println("player enter"+user.getUid());
                user.send(bytes);
            }
        }
    }

    public void alluser(){
        for (Iterator iterator = users.values().iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();
            if (user != null) {
                System.out.println(user.getUid());
            }
        }
    }

}
