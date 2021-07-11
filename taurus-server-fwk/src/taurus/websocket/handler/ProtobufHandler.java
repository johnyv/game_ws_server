package taurus.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import taurus.websocket.protobuf.ProtobufMsg;

public interface ProtobufHandler {
    public void handle(ChannelHandlerContext context, ProtobufMsg protobufMsg);
}
