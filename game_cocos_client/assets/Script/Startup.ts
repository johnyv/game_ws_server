const { ccclass, property } = cc._decorator;
import { NET } from "./net/NetHandler";

@ccclass
export default class Startup extends cc.Component {

    // LIFE-CYCLE CALLBACKS:

    onLoad() {
        NET.init("ws://127.0.0.1:8090/ws");
        NET.connect(() => {
            cc.log("websocket connected.");
            cc.director.loadScene("login");
        });

    }

    start() {

    }

    // update (dt) {}
}
