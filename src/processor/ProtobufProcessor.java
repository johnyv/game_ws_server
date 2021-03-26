package processor;

import group.ClientInfo;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.data.RecvPacket;

public class ProtobufProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(ProtobufProcessor.class);
    private static ProtobufProcessor instance = new ProtobufProcessor();
    protected ChannelHandlerContext ctx;

    public ProtobufProcessor() {
    }

    public static ProtobufProcessor getInstance() {
        return instance;
    }

    @Override
    public void process(ClientInfo client, RecvPacket packet) throws Exception {

    }

//    @Override
//    public void init(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
//        this.ctx = ctx;
////        player = Player.parseFrom(bytes);
//    }
//
//    @Override
//    public void process(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
//        init(ctx, bytes);
//    }
//
//    @Override
//    public void send(String msg) {
//
//    }
}
