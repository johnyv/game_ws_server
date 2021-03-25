package processor;

import io.netty.channel.ChannelHandlerContext;

public interface Processor {
    void init(ChannelHandlerContext ctx, byte[] bytes) throws Exception;

    void process(ChannelHandlerContext ctx, byte[] bytes) throws Exception;

    void send(String msg);
}
