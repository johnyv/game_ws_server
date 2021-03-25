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
        logger.info("Protobuf...");

//        logger.info("id:"+player.getId());
//        String name = player.getName();
//        logger.info("name:"+name);
//        logger.info("time:"+player.getEnterTime());
//
//        ctx.channel().writeAndFlush(ProtobufMessageUtil.getFrame(player));
    }

    @Override
    public void send(String msg) {
//        Player p = Player.newBuilder(player).setName(msg).build();
//        ctx.channel().writeAndFlush(ProtobufMessageUtil.getFrame(p));
    }
}
