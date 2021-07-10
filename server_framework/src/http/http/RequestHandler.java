package http.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public interface RequestHandler {
    public void handle(ChannelHandlerContext context, HttpRequest request, String data);
}