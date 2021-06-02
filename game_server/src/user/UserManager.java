package user;

import io.netty.channel.group.ChannelGroup;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private final ConcurrentHashMap<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> userList = new ConcurrentHashMap<>();

    private static UserManager instance = new UserManager();
    public static UserManager getInstance(){return instance;}

    public UserManager(){}

    public void addUser(User user){
        if (userList.contains(user.getUid())){
            return;
        }
        userList.put(user.getUid(), user);
    }

    public void removeUserBySessionId(String sessionId){
        for (Iterator iterator = userList.values().iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();
            if (user.getSessionId() == sessionId) {
                userList.remove(user.getUid());
            }
        }
    }

    public ConcurrentHashMap getUserList(){
        return userList;
    }

    public void sendToAll(byte[] bytes){
        for (Iterator iterator = userList.values().iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();
            if (user != null) {
                System.out.println("player enter"+user.getUid());
                user.send(bytes);
            }
        }
    }

    public void alluser(){
        for (Iterator iterator = userList.values().iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();
            if (user != null) {
                System.out.println(user.getUid());
            }
        }
    }

}
