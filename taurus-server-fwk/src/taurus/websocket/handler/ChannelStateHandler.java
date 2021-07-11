package taurus.websocket.handler;

import io.netty.channel.ChannelHandlerContext;

public interface ChannelStateHandler {
    public void handle(ChannelHandlerContext context);
}
