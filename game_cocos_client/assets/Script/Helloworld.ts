const { ccclass, property } = cc._decorator;

import { protocol } from "../proto/proto";
import HBInfo = protocol.HBInfo;
import MotionInfo = protocol.MotionInfo;
import LoginInfo = protocol.LoginInfo;
import UserList = protocol.UserList;
import Player = protocol.Player;

import { NET } from "./net/NetHandler";
import { EVENTS } from "./util/EventHandler";
import { USERMANAGER } from "./user/UserManager";

import ProtoMsg from "./util/ProtoMsg";
import User from "./user/User";
import { LOCALDATA } from "./data/LocalData";

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
        this.cocos.node.active = false;
    }

    onLoad() {
        let self = this;

        let user: User = new User(LOCALDATA.getUid(), this.cocos);
        user.addToScene();
        USERMANAGER.add(user);

        EVENTS.on(1001, this.onHeart);
        EVENTS.on(1004, this.onUserList.bind(this));
        EVENTS.on(2002, this.onPlayerEnter.bind(this));
        EVENTS.on(2003, this.onPlayerMove.bind(this));

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

        let arr = new Uint8Array(0);
        let msg = ProtoMsg.pack(1004, arr);
        NET.send(msg);
    }

    onHeart(data: Uint8Array) {
        let decoded = HBInfo.decode(data);
        // cc.log(decoded);
    }

    onUserList(data: Uint8Array) {
        let decoded = UserList.decode(data);
        // cc.log(decoded);
        let uList = decoded.userList;
        for (let i = 0; i < uList.length; i++) {
            let p = uList[i];
            // let user: User = USERMANAGER.getUser(p.id);
            if (p.id != LOCALDATA.getUid() && USERMANAGER.getUser(p.id) == null) {
                let user: User = new User(p.id, this.cocos);
                user.addToScene();
                USERMANAGER.add(user);
            }
        }
    }

    onPlayerEnter(data: Uint8Array) {
        let decoded = LoginInfo.decode(data);
        cc.log("Player enter.");

        let user: User = new User(decoded.id, this.cocos);
        user.addToScene();
        USERMANAGER.add(user);

        cc.log(decoded);
    }

    onPlayerMove(data: Uint8Array) {
        let decoded = MotionInfo.decode(data);
        cc.log("Player move.");
        var pt: cc.Vec2 = new cc.Vec2(decoded.x, decoded.y);

        // this.cocos.node.runAction(
        //     cc.moveTo(1, pt)
        // );
        let worldPt = this.node.convertToNodeSpaceAR(pt);

        let user: User = USERMANAGER.getUser(decoded.uid.toString());
        if (user) {
            user.move(pt);
        }
        cc.log(decoded);
    }

    onTouchEnd(event: cc.Event.EventTouch) {
        var pt: cc.Vec2 = event.getLocation();

        let scene = cc.director.getScene();
        let worldPt = scene.convertToNodeSpaceAR(pt);
        cc.log("move x:", worldPt.x);
        cc.log("move y:", worldPt.y);

        var msg = MotionInfo.create({ uid: LOCALDATA.getUid(), x: worldPt.x, y: worldPt.y });
        var uint8Arr = MotionInfo.encode(msg).finish();

        var msgBuf = ProtoMsg.pack(1003, uint8Arr);
        NET.send(msgBuf);

        let user: User = USERMANAGER.getUser(LOCALDATA.getUid());
        if (user) {
            user.move(worldPt);
        }

        // this.cocos.node.runAction(
        //     cc.moveTo(1, worldPt)
        // );
    }
}
