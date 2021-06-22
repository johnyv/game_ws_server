const { ccclass, property } = cc._decorator;

@ccclass
export default class FrameAnim extends cc.Component {

    @property([cc.SpriteFrame])
    frames: Array<cc.SpriteFrame> = [];

    @property
    duration: number = 0.1;
    @property
    loop: boolean = false;
    @property
    playOnload: boolean = false;

    private endFunc: any = null;
    private sprite: cc.Sprite;
    private isPlaying: boolean = false;
    private playTime: number = 0;
    // LIFE-CYCLE CALLBACKS:

    anims: Map<string, Array<cc.SpriteFrame>> = new Map();

    onLoad() {
        this.sprite = this.node.getComponent(cc.Sprite);
        if (!this.sprite) {
            this.sprite = this.node.addComponent(cc.Sprite);
        }

        if (this.playOnload) {
            if (this.loop) {

            } else {

            }
        }
    }

    start() {

    }

    update(dt) {
        if (!this.isPlaying) {
            return;
        }

        this.playTime += dt;
        let index: number = Math.floor(this.playTime / this.duration);
        if (this.loop) {
            if (index >= this.frames.length) {
                index -= this.frames.length;
                this.playTime -= (this.duration * this.frames.length)
            }

            this.sprite.spriteFrame = this.frames[index];
        } else {
            if (index >= this.frames.length) {
                this.isPlaying = false;
                if (this.endFunc) {
                    this.endFunc();
                }
            } else {
                this.sprite.spriteFrame = this.frames[index];
            }
        }
    }

    private initFrame(loop: boolean, endf: any): void {
        if (this.frames.length <= 0) {
            return;
        }

        this.isPlaying = true;
        this.playTime = 0;
        this.sprite.spriteFrame = this.frames[0];
        this.loop = loop;
        this.endFunc = endf;
    }

    public playLoop(): void {
        this.initFrame(true, null);
    }

    public playOnce(endf: any): void {
        this.initFrame(false, endf);
    }

    public setFrames(frames: Array<cc.SpriteFrame>): void {
        this.frames = frames;
    }

    public addAnim(name: string, frames: Array<cc.SpriteFrame>): void {
        this.anims.set(name, frames);
    }

    public playAnim(name: string, loop: boolean, endf: any): void {
        let frames = this.anims.get(name);
        if (frames != undefined) {
            this.frames = frames;
        }
        if (loop) {
            this.playLoop();
        } else {
            this.playOnce(endf)
        }
    }
}
