package handler;

import io.netty.channel.ChannelHandlerContext;
import taurus.websocket.handler.ChannelStateHandler;

public class ChannelInactiveHandler implements ChannelStateHandler {
    @Override
    public void handle(ChannelHandlerContext context) {
        System.out.println("ChannelInactiveHandler");
    }
}
