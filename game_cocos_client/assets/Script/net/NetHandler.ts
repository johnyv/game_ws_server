import EventHandler from "../util/EventHandler";

class NetHandler {
    private static instance: NetHandler;

    private webSocket: WebSocket;
    private address: string;
    private handler: EventHandler;
    public isConnected: boolean;
    constructor() {
    }

    public static getInstance(): NetHandler {
        this.instance || (this.instance = new NetHandler());
        return this.instance;
    }

    public init(address: string): void {
        this.address = address;
        this.handler = EventHandler.getInstance();
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
            cc.log("onmessage");
            cc.log(event);
            self.process(event);
        };
        this.webSocket.onerror = function (event: Event) { };

        this.webSocket.onclose = function (event: CloseEvent) {
            self.handler.removeAllListeners();
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
        cc.log("process---event.data");
        cc.log(event.data);
        this.handler.emit(event.data);
    }
}

export const netHandler = NetHandler.getInstance();