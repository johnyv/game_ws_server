export default class User {
    private uid: string;
    private uImage: cc.Node;

    constructor(uid: string, img: cc.Sprite) {
        this.uid = uid;
        this.uImage = cc.instantiate(img.node);
        this.uImage.active = true;
    }
    public getUid(): string {
        return this.uid;
    }

    public setUid(uid: string) {
        this.uid = uid;
    }

    public addToScene() {
        let scene = cc.director.getScene();
        this.uImage.setAnchorPoint(0.5, 0.5);
        this.uImage.setPosition(300, 200);
        scene.addChild(this.uImage);
    }

    public move(pt: cc.Vec2) {
        // let ptt = this.uImage.convertToNodeSpaceAR(pt);
        // this.uImage.runAction(
        //     cc.moveTo(1, pt)
        // );
        cc.tween(this.uImage).to(
            1,{x:pt.x, y:pt.y}
        ).start();
    }
}
