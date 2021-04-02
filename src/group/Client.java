package group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Client {
    public static final AttributeKey<Client> CHANNEL_INFO = AttributeKey.valueOf("channelInfo");
    private String channelId;
    private String uid;
    private ChannelHandlerContext ctx;
    private Set<String> group;

    private Queue<Runnable> quests = null;

    public Client(ChannelHandlerContext ctx, String id) {
        quests = new ConcurrentLinkedDeque<Runnable>();
        this.ctx = ctx;
        channelId = ctx.channel().id().asLongText();
        uid = id;
        ClientCenter.INSTANCE.addMember(this);
    }

    public String getChannelId() {
        return channelId;
    }

    public Channel getChannel() {
        return ctx.channel();
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
}
