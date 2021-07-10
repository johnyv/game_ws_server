package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    private HttpServer server;
    private HttpRequest request;
    private ByteBuf body;

    public HttpServerHandler(HttpServer server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg)
            throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent)msg;
            if (this.body == null) {
                this.body = Unpooled.buffer();
            }
            this.body.writeBytes(content.content());
            content.release();
            if (msg instanceof LastHttpContent) {
                server.request(context, request, body.toString(CharsetUtil.UTF_8));
                body.release();
                body = null;
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
