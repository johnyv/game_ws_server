package message;

import io.netty.channel.Channel;
import message.data.RecvPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProtobufProcessor extends MessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(ProtobufProcessor.class);
//    private static ProtobufProcessor instance = new ProtobufProcessor();
    protected Channel channel;
    protected RecvPacket packet;
    public ProtobufProcessor() {
    }

//    public static ProtobufProcessor getInstance() {
//        return instance;
//    }


    @Override
    public void process(Channel channel, byte[] bytes) throws Exception {
        this.channel = channel;
        packet = new RecvPacket(bytes);
        logger.info("Protobuf...");

//        logger.info("id:"+player.getId());
//        String name = player.getName();
//        logger.info("name:"+name);
//        logger.info("time:"+player.getEnterTime());
//
//        ctx.channel().writeAndFlush(ProtobufMessageUtil.getFrame(player));
    }
}
