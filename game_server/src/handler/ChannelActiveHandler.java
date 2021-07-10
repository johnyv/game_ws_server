package handler;

import io.netty.channel.ChannelHandlerContext;
import websocket.handler.ChannelStateHandler;

public class ChannelActiveHandler implements ChannelStateHandler {
    @Override
    public void handle(ChannelHandlerContext context) {
        System.out.println("ChannelActiveHandler");
    }
}
