package websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import websocket.protobuf.ProtobufMsg;

public interface ProtobufHandler {
    public void handle(ChannelHandlerContext context, ProtobufMsg protobufMsg);
}
