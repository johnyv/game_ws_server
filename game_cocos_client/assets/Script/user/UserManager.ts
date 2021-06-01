import User from "./User"

class UserManager {
    private static instance: UserManager;
    private users: Map<string, User>;

    public static getInstance(): UserManager {
        this.instance || (this.instance = new UserManager());
        return this.instance;
    }

    constructor() {
        this.users = new Map();
    }
    
    public add(user: User): void {
        this.users.set(user.getUid(), user);
    }

    public getUser(uid: string): User {
        return this.users.get(uid);
    }
}

export const USERMANAGER = UserManager.getInstance(); 
