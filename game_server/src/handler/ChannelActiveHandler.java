package handler;

import io.netty.channel.ChannelHandlerContext;
import taurus.websocket.handler.ChannelStateHandler;

public class ChannelActiveHandler implements ChannelStateHandler {
    @Override
    public void handle(ChannelHandlerContext context) {
        System.out.println("ChannelActiveHandler");
    }
}
