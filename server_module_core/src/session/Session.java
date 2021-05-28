package session;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Session {
    private String channelId;
    private String id;
    private volatile Channel channel;
    private Set<String> group;

    private Queue<Runnable> quests = null;

    public Session(ChannelHandlerContext ctx) {
        quests = new ConcurrentLinkedDeque<Runnable>();
        channel = ctx.channel();
        channelId = channel.id().asLongText();
        id = channel.id().asLongText();
        SessionManager.getInstance().add(this);
    }

    public String getChannelId() {
        return channelId;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean addQuest(Runnable quest) {
        return quests.add(quest);
    }

    public void pollQuest() {
        Runnable quest = quests.poll();
        if (quest != null) {
            try {
                quest.run();
            } catch (Exception e) {

            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isConnected() {
        if (channel != null) {
            return channel.isActive();
        }
        return false;
    }

    public void write(byte[] bytes) {
        if (channel != null) {
            ByteBuf buf = Unpooled.copiedBuffer(bytes);
            channel.writeAndFlush(new BinaryWebSocketFrame(buf));
        }
    }

    public void close() {
        if (channel != null) {
            channel.close();
        }
    }
}
