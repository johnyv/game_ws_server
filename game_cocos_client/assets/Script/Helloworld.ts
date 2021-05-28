const { ccclass, property } = cc._decorator;

import { protocol } from "../proto/proto";
import HBInfo = protocol.HBInfo;
import MotionInfo = protocol.MotionInfo;
import LoginInfo = protocol.LoginInfo;

import { NET } from "./net/NetHandler";
import { EVENTS } from "./util/EventHandler";
import ProtoMsg from "./util/ProtoMsg";

@ccclass
export default class Helloworld extends cc.Component {
    @property(cc.Sprite)
    cocos: cc.Sprite = null;

    @property(cc.Label)
    label: cc.Label = null;

    @property
    text: string = 'hello';

    start() {
        // init logic
        this.label.string = this.text;
        cc.log(this.cocos);
    }

    onLoad() {
        let self = this;

        EVENTS.on(1001, this.onHeart);
        EVENTS.on(2002, this.onPlayerEnter);
        EVENTS.on(2003, this.onPlayerMove);

        this.schedule(() => {
            if (NET.isConnected) {
                let msg = HBInfo.create({
                    systemCurrtime: new Date().getTime()
                });
                let uint8Arr = HBInfo.encode(msg).finish();

                let msgBuf = ProtoMsg.pack(1001, uint8Arr);
                NET.send(msgBuf);
            }
        }, 3);

        this.node.on(cc.Node.EventType.TOUCH_END, this.onTouchEnd.bind(this));
    }

    onHeart(data: Uint8Array) {
        let decoded = HBInfo.decode(data);
        cc.log(decoded);
    }

    onPlayerEnter(data: Uint8Array) {
        let decoded = LoginInfo.decode(data);
        cc.log("Player enter.");
        cc.log(decoded);
    }

    onPlayerMove(data: Uint8Array) {
        let decoded = MotionInfo.decode(data);
        cc.log("Player enter.");
        cc.log(decoded);
    }

    onTouchEnd(event: cc.Event.EventTouch) {
        var pt: cc.Vec2 = new cc.Vec2(event.getLocationX() - 480, event.getLocationY() - 320);
        cc.log("move x:", pt.x);
        cc.log("move y:", pt.y);

        var msg = MotionInfo.create({ uid: 11,x: pt.x, y: pt.y });
        var uint8Arr = MotionInfo.encode(msg).finish();

        var msgBuf = ProtoMsg.pack(1003, uint8Arr);
        NET.send(msgBuf);

        this.cocos.node.runAction(
            cc.moveTo(1, pt)
        );
    }
}
