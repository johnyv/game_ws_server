package user;

import session.Session;

public class User {
    private String uid;
//    private String uname;
    private Session session;

    public User(String uid, Session session) {
        this.uid = uid;
//        this.uname = uname;
        this.session = session;
    }

    public String getUid() {
        return uid;
    }

    public String getSessionId(){
        return session.getId();
    }

    public void send(byte[] bytes){
        session.write(bytes);
    }
}
