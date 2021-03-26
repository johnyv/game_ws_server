package processor;

import group.ClientInfo;
import io.netty.channel.ChannelHandlerContext;
import processor.data.RecvPacket;

public abstract class Processor {
    //    void init(ChannelHandlerContext ctx, byte[] bytes) throws Exception;
    protected int code = 0;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public abstract void process(ClientInfo client, RecvPacket packet) throws Exception;

//    void send(String msg);
}
