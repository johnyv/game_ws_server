package message;

import io.netty.channel.Channel;

public abstract class MessageProcessor {
    abstract void process(Channel channel, byte[] bytes) throws Exception;
}
