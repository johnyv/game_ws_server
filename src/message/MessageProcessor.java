package message;

import io.netty.channel.ChannelHandlerContext;

public interface MessageProcessor {
    void init(ChannelHandlerContext ctx, byte[] bytes) throws Exception;

    void process(ChannelHandlerContext ctx, byte[] bytes) throws Exception;

    void send(String msg);
}
