class EventHandler {
    private static instance: EventHandler;
    private listener: Object;

    constructor() {
        this.listener = {};
    }

    public static getInstance(): EventHandler {
        this.instance || (this.instance = new EventHandler());
        return this.instance;
    }

    public emit(event: PropertyKey, data: any): void {
        var handler: any;
        if (this.listener.hasOwnProperty(event)) {
            var event_list = this.listener[event];
            for (var i = 0; i < event_list.length; ++i) {
                handler = event_list[i];
                // var args = [];

                // for (var n = 1; n < arguments.length; ++n) {
                //     args.push(arguments[n]);
                // }

                // handler.apply(this, args);
                handler.apply(this, [data]);
            }
        }
    }

    public on(type: PropertyKey, handler: any): void {
        if (this.listener.hasOwnProperty(type)) {
            this.listener[type].push(handler);
        } else {
            this.listener[type] = [handler];
        }
    }

    public removeListener(type: PropertyKey, handler: any): boolean {
        if (!this.listener.hasOwnProperty(type)) {
            return false;
        }
        let index = this.listener[type].indexOf(handler);
        if (index >= 0) {
            this.listener[type].splice(index, 1);
        }
    }

    public removeAllListeners(): void {
        this.listener = {};
    }
}

export const EVENTS = EventHandler.getInstance();