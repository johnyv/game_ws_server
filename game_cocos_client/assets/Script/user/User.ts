export default class User {
    private uid: string;
    private uImage: cc.Node;

    constructor(uid: string, img: cc.Sprite) {
        this.uid = uid;
        this.uImage = cc.instantiate(img.node);
    }
    public getUid(): string {
        return this.uid;
    }

    public setUid(uid: string) {
        this.uid = uid;
    }

    public addToScene() {
        let scene = cc.director.getScene();
        this.uImage.setPosition(30, 30);
        cc.log(this.uImage);
        scene.addChild(this.uImage);
    }

    public move(pt: cc.Vec2) {
        this.uImage.runAction(
            cc.moveTo(1, pt)
        );
    }
}
