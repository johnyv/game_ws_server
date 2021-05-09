const { ccclass, property } = cc._decorator;
import { netHandler } from "./net/NetHandler";

@ccclass
export default class Startup extends cc.Component {

    //netHandler:NetHandler = NetHandler.getInstance();

    // LIFE-CYCLE CALLBACKS:

    onLoad() {
        // this.netHandler = NetHandler.getInstance();
        netHandler.init("ws://127.0.0.1:8090/websocket");
        netHandler.connect(() => {
            cc.log("websocket connected.");
            cc.director.loadScene("helloworld");
        });

    }

    start() {

    }

    // update (dt) {}
}
