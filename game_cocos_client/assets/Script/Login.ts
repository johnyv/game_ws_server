const { ccclass, property } = cc._decorator;

import { protocol } from "../proto/proto";
import LoginInfo = protocol.LoginInfo;

import { NET } from "./net/NetHandler";
import { EVENTS } from "./util/EventHandler";
import MsgPack from "./util/MsgPack";

import {LOCALDATA} from "./data/LocalData";

@ccclass
export default class NewClass extends cc.Component {

    @property(cc.Button)
    loginBut: cc.Button = null;

    @property(cc.EditBox)
    idEditbox: cc.EditBox = null;

    @property(cc.EditBox)
    nameEditbox: cc.EditBox = null
    // LIFE-CYCLE CALLBACKS:

    onLoad() {
        var self = this;
        this.loginBut.node.on(cc.Node.EventType.TOUCH_END, function (event: cc.Event.EventTouch) {
            cc.log(self.idEditbox.string);
            cc.log(self.nameEditbox.string);
            if (NET.isConnected) {
                let msg = LoginInfo.create({
                    id:self.idEditbox.string,
                    pwd:self.nameEditbox.string,
                    enterTime: new Date().getTime()
                });

                LOCALDATA.setUid(self.idEditbox.string);
                                
                let uint8Arr = LoginInfo.encode(msg).finish();

                let msgBuf = MsgPack.pack(1002, uint8Arr);
                NET.send(msgBuf);
            }

        });

        EVENTS.on(1002, this.onLogin);
    }

    start() {

    }

    onLogin(data:Uint8Array){
        cc.director.loadScene("helloworld");
    }
    // update (dt) {}
}
