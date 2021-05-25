package logic.proc;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import logic.protocol.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import abstracted.Processor;
import packet.MsgPack;
import session.Session;

public class PlayerProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(PlayerProcessor.class);

    protected ChannelHandlerContext ctx;

    Player player;

    public PlayerProcessor() {
    }

    @Override
    protected void init(Session session, MsgPack packet) {
        try {
            player = Player.parseFrom(packet.getData());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(Session session, MsgPack packet) throws Exception {
        logger.info("process player...");
        init(session, packet);

        logger.info("length:" + packet.getLength());
        logger.info("code:" + packet.getCode());

        logger.info("id:" + player.getId());
        String name = player.getName();
        logger.info("name:" + name);
        logger.info("time:" + player.getEnterTime());

        byte[] bytes = MsgPack.pack(packet.getCode(), player);
        session.write(bytes);
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
