import User from "../user/User";

class localData {
    private static instance: localData;
    private uid: string;
    private user: User;

    public static getInstance(): localData {
        this.instance || (this.instance = new localData());
        return this.instance;
    }

    constructor() { }
    public setUid(uid: string) {
        this.uid = uid;
    }

    public getUid(): string {
        return this.uid;
    }

    public setLocalUser(user: User) {
        this.user = user;
    }

    public getLocalUser(): User {
        return this.user;
    }
}

export const LOCALDATA = localData.getInstance();
