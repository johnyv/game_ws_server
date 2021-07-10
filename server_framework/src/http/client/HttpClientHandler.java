package http.client;

import http.future.Future;
import http.http.Response;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientHandler extends ChannelInboundHandlerAdapter {
    private HttpClient client;
    private DefaultHttpResponse response;
    private ByteBuf body;

    public HttpClientHandler(HttpClient client) {
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            this.response = (DefaultHttpResponse) msg;
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            if (this.body == null) {
                this.body = Unpooled.buffer();
            }
            this.body.writeBytes(content.content());
            content.release();
            if (msg instanceof LastHttpContent) {
                Response response = new Response(this.response, this.body);
                if (client.getRequest().getFuture().getState().equals(Future.FutureState.Running)) {
                    client.getRequest().getFuture().success(response);
                } else {
                    log.warn("finished, incorrect state");
                }
                client.ready();
                this.body = null;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        client.ready();
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        client.disconnected();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        super.exceptionCaught(channelHandlerContext, throwable);
        client.exception(throwable);
    }
}
