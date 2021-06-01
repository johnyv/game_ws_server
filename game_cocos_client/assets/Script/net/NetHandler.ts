import { EVENTS } from "../util/EventHandler";
import ProtoMsg from "../util/ProtoMsg";

class NetHandler {
    private static instance: NetHandler;

    private webSocket: WebSocket;
    private address: string;
    public isConnected: boolean;
    constructor() {
    }

    public static getInstance(): NetHandler {
        this.instance || (this.instance = new NetHandler());
        return this.instance;
    }

    public init(address: string): void {
        this.address = address;
        this.isConnected = false;
        this.webSocket = null;
    }

    public connect(cb: Function): void {
        var self = this;
        this.webSocket = new WebSocket(this.address);
        this.webSocket.onopen = function (event: Event) {
            self.webSocket.binaryType = 'arraybuffer';
            self.isConnected = true;
            if (cb) {
                cb.call(self);
            }
        };
        this.webSocket.onmessage = function (event: MessageEvent) {
            self.process(event);
        };
        this.webSocket.onerror = function (event: Event) { };

        this.webSocket.onclose = function (event: CloseEvent) {
            self.close();
        };
    }

    public send(data: any): void {
        this.webSocket.send(data);
    }

    public close(): void {
        if (!this.webSocket) {
            return;
        }

        try {
            this.webSocket.close();
        } catch (err) {
            cc.log('error while closing webSocket', err.toString());
        }
        this.webSocket = null;
    }

    private process(event: MessageEvent): void {
        var arrBuf:ArrayBuffer = event.data as ArrayBuffer;
        var byteArr:Uint8Array = new Uint8Array(arrBuf);
        cc.log(byteArr.byteLength);
        
        var msg = ProtoMsg.unpack(arrBuf);
        EVENTS.emit(msg.code, msg.dataBytes);
    }
}

export const NET = NetHandler.getInstance();