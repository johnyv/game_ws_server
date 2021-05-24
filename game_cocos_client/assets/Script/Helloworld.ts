const { ccclass, property } = cc._decorator;

import { protocol } from "../proto/proto";
import HeartBeat = protocol.HeartBeat;
import Player = protocol.Player;

import { netHandler } from "./net/NetHandler";
@ccclass
export default class Helloworld extends cc.Component {

    @property(cc.Label)
    label: cc.Label = null;

    @property
    text: string = 'hello';
    // netHandler:NetHandler = NetHandler.getInstance();

    start() {
        // init logic
        this.label.string = this.text;
    }

    onLoad() {
        // cc.director.loadScene("startup");
        let self = this;
        let msg = HeartBeat.create({
            systemCurrtime: new Date().getTime()
        });
        let buf = HeartBeat.encode(msg).finish();
        this.scheduleOnce(() => {
            let decoded = HeartBeat.decode(buf);
            cc.log("--->", buf);
            cc.log(decoded);
        }, 2);

        var arrayBuf = new ArrayBuffer(buf.length+8);
        var databuf = new DataView(arrayBuf);
        databuf.setInt32(0, buf.length+8);
        databuf.setUint32(4,1001);

        for(var i =0; i < buf.length; i++){
            databuf.setUint8(i+8, buf[i]);
        }

        netHandler.send(databuf);
        // self.netHandler = NetHandler.getInstance();
        // this.netHandler.init("ws://127.0.0.1:8090/websocket");
        // this.netHandler.connect(()=>{
        // self.netHandler.send("test...");
        // });

        this.schedule(() => {
            // cc.log("=>" + netHandler.isConnected);
            if (netHandler.isConnected) {
                netHandler.send(databuf);
                // cc.log("send...");
                // netHandler.send("test...");
            }
        }, 3);
    }
}
