package user;

import session.Session;

public class User {
    private int uid;
    private String uname;
    private Session session;

    public User(int uid, String uname, Session session) {
        this.uid = uid;
        this.uname = uname;
        this.session = session;
    }
}
