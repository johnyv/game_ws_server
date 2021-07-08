package logic.proc;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import logic.protocol.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import netty.abstracted.Processor;
import netty.packet.ProtoMsg;
import netty.session.Session;

public class PlayerProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(PlayerProcessor.class);

    protected ChannelHandlerContext ctx;

    Player player;

    public PlayerProcessor() {
    }

    @Override
    protected void init(Session session, ProtoMsg protoMsg) {
        try {
            player = Player.parseFrom(protoMsg.getData());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(Session session, ProtoMsg protoMsg) throws Exception {
        init(session, protoMsg);

        String name = player.getName();
        logger.info("name:" + name);

        byte[] bytes = ProtoMsg.pack(protoMsg.getCode(), player);
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
