package user;

import session.Session;

public class User {
    private String uid;
    private String uname;
    private Session session;

    public User(String uid, String name, Session session) {
        this.uid = uid;
        this.uname = name;
        this.session = session;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return uname;
    }

    public String getSessionId(){
        return session.getId();
    }

    public void send(byte[] bytes){
        session.write(bytes);
    }
}
