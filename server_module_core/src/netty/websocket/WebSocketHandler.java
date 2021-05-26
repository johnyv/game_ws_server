package netty.websocket;

import dispatcher.Dispatcher;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import packet.ProtoMsg;
import session.Session;

public class WebSocketHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    private WebSocketServerHandshaker handShaker;
    private static final String WEBSOCKET_PATH = "/ws";
    private boolean SSL;

    public WebSocketHandler(boolean useSSL) {
        SSL = useSSL;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        SocketChannel channel = (SocketChannel) ctx.channel();
        logger.info("address:" + ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("close:" + ctx.channel().localAddress().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.info(cause.getMessage());
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
            TextWebSocketFrame tFrame = (TextWebSocketFrame) frame;
            String txt = "TextWebSocketFrame:" + tFrame.text();
            ctx.channel().write(new TextWebSocketFrame(txt));
            return;
        }
        if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame bFrame = (BinaryWebSocketFrame) frame;
            byte[] bytes = new byte[bFrame.content().readableBytes()];
            bFrame.content().readBytes(bytes);

            try {
                ProtoMsg pack = ProtoMsg.unPack(bytes);
                Session session = new Session(ctx);
                Dispatcher.getInstance().dispatch(session, pack);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("parse failed.");
            }
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
