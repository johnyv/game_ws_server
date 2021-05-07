const { ccclass, property } = cc._decorator;

import { protocol } from "../proto/proto";
import Player = protocol.Player;

@ccclass
export default class Helloworld extends cc.Component {

    @property(cc.Label)
    label: cc.Label = null;

    @property
    text: string = 'hello';

    start() {
        // init logic
        this.label.string = this.text;
    }

    onLoad() {
        let msg = Player.create({
            name: "test",
            id: 123, enterTime: 321
        });
        let buf = Player.encode(msg).finish();
        this.scheduleOnce(() => {
            let decoded = Player.decode(buf);
            cc.log("--->", buf);
            cc.log(decoded);
        }, 2);
    }
}
