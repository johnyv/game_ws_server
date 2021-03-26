package group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.Set;

public class ClientInfo {
    public static final AttributeKey<ClientInfo> CHANNEL_INFO = AttributeKey.valueOf("channelInfo");
    private String channelId;
    private String uid;
    private Channel channel;
    private Set<String> group;

    public ClientInfo(ChannelHandlerContext ctx, String id){
        channel = ctx.channel();
        channelId = channel.id().asLongText();
        uid = id;
        GroupManager.INSTANCE.addMember(this);
    }

    public String getChannelId() {
        return channelId;
    }

    public Channel getChannel() {
        return channel;
    }
}
