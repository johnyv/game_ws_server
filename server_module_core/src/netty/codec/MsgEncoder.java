package netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import packet.Message;

import java.util.List;

public class MsgEncoder extends MessageToMessageEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message msg, List<Object> out) throws Exception {

    }
}
