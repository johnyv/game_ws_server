<<<<<<< HEAD
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
=======
package processor;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtobufProcessor implements Processor {
    private static Logger logger = LoggerFactory.getLogger(ProtobufProcessor.class);
    private static ProtobufProcessor instance = new ProtobufProcessor();
    protected ChannelHandlerContext ctx;

    public ProtobufProcessor() {
    }

    public static ProtobufProcessor getInstance() {
        return instance;
    }

    @Override
    public void init(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
        this.ctx = ctx;
//        player = Player.parseFrom(bytes);
    }

    @Override
    public void process(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
        init(ctx, bytes);
>>>>>>> 698a5de9e4296c165f17f4c968b91ee5fd2ab002
        logger.info("Protobuf...");

//        logger.info("id:"+player.getId());
//        String name = player.getName();
//        logger.info("name:"+name);
//        logger.info("time:"+player.getEnterTime());
//
//        ctx.channel().writeAndFlush(ProtobufMessageUtil.getFrame(player));
    }
<<<<<<< HEAD
=======

    @Override
    public void send(String msg) {
//        Player p = Player.newBuilder(player).setName(msg).build();
//        ctx.channel().writeAndFlush(ProtobufMessageUtil.getFrame(p));
    }
>>>>>>> 698a5de9e4296c165f17f4c968b91ee5fd2ab002
}
