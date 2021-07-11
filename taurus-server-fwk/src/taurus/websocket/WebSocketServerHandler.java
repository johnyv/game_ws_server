package taurus.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketServerHandler extends ChannelInboundHandlerAdapter {
    private WebSocketServerHandshaker handShaker;
    private WebSocketServer server;
    private static final String WEBSOCKET_PATH = "/ws";
    private boolean SSL;

    public WebSocketServerHandler(WebSocketServer server) {
        this.server = server;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        server.handleChannelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        server.handleChannelInactive(ctx);
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) obj);
        } else if (obj instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) obj);
        } else {
            throw new Exception("parse failed.");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.info(cause.getMessage());
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess()) {
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);

            if (httpResponse.status().code() != 200) {
                ByteBuf buf = Unpooled.copiedBuffer(httpResponse.status().toString(), CharsetUtil.UTF_8);
                httpResponse.content().writeBytes(buf);
                buf.release();
            }

            ChannelFuture f = ctx.channel().writeAndFlush(httpResponse);
            if (httpResponse.status().code() != 200) {
                f.addListener(ChannelFutureListener.CLOSE);
            }

            return;
        }

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWSLocation(request),
                null, true, 5 * 1024 * 1024);
        handShaker = wsFactory.newHandshaker(request);

        if (null == handShaker) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), request);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            handShaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }

        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (frame instanceof TextWebSocketFrame) {
            String str = "TextWebSocketFrame:" + ((TextWebSocketFrame) frame).text();
            ctx.channel().write(new TextWebSocketFrame(str));
            return;
        }

        if (frame instanceof BinaryWebSocketFrame) {
            server.handle(ctx, (BinaryWebSocketFrame) frame);
            return;
        }
    }

    private String getWSLocation(FullHttpRequest req) {
        String location = req.headers().get(HttpHeaderNames.HOST) + WEBSOCKET_PATH;
        if (SSL) {
            return "wss://" + location;
        } else {
            return "ws://" + location;
        }
    }
}
