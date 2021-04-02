package protobuf.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import group.Client;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.Processor;
import processor.data.RecvPacket;
import processor.data.SendPacket;
import protobuf.player.Player;

public class PlayerProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(PlayerProcessor.class);

    protected ChannelHandlerContext ctx;

    Player player;
    public PlayerProcessor() {
    }

    @Override
    protected void init(Client client, RecvPacket packet)  {
        try {
            player = Player.parseFrom(packet.getData());
        }catch (InvalidProtocolBufferException e){
            e.printStackTrace();
        }
    }

    @Override
    public void process(Client client, RecvPacket packet) throws Exception {
        logger.info("process player...");
        init(client, packet);

        logger.info("length:"+packet.getLength());
        logger.info("code:"+packet.getCode());

        logger.info("id:"+player.getId());
        String name = player.getName();
        logger.info("name:"+name);
        logger.info("time:"+player.getEnterTime());

        SendPacket pack = new SendPacket(packet.getCode(), player);
        ByteBuf buf = Unpooled.copiedBuffer(pack.getData());

        client.getChannel().writeAndFlush(new BinaryWebSocketFrame(buf));
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