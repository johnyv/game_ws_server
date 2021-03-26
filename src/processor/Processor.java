package processor;

import io.netty.channel.Channel;

<<<<<<< HEAD:src/message/MessageProcessor.java
public abstract class MessageProcessor {
    abstract void process(Channel channel, byte[] bytes) throws Exception;
=======
public interface Processor {
    void init(ChannelHandlerContext ctx, byte[] bytes) throws Exception;

    void process(ChannelHandlerContext ctx, byte[] bytes) throws Exception;

    void send(String msg);
>>>>>>> 698a5de9e4296c165f17f4c968b91ee5fd2ab002:src/processor/Processor.java
}
