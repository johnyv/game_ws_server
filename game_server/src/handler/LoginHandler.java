package handler;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import logic.protocol.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import taurus.websocket.protobuf.ProtobufMsg;
import taurus.websocket.handler.ProtobufHandler;

@Slf4j
public class LoginHandler implements ProtobufHandler {
    @Override
    public void handle(ChannelHandlerContext context, ProtobufMsg protobufMsg) {
        LoginInfo info;
        try {
            info = LoginInfo.parseFrom(protobufMsg.getData());
            log.info("login id:" + info.getId());

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
