package taurus.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import taurus.utils.WebSocketUtil;
import taurus.websocket.handler.ChannelStateHandler;
import taurus.websocket.handler.ProtobufHandler;
import taurus.websocket.protobuf.ProtobufMsg;

import javax.net.ssl.SSLContext;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WebSocketServer {
    private volatile EventLoopGroup workerGroup;
    private volatile EventLoopGroup bossGroup;
    private volatile ServerBootstrap bootstrap;

    private volatile int port;

    private ChannelStateHandler channelActiveHandler = null;
    private ChannelStateHandler channelInactiveHandler = null;

    private volatile Map<Integer, ProtobufHandler> handlerMap = new HashMap<Integer, ProtobufHandler>();

    private final SSLContext sslContext;

    public WebSocketServer(int port, SSLContext sslContext) {
        this.port = port;
        this.sslContext = sslContext;
    }

    public void addHandler(Integer code, ProtobufHandler handler) {
        handlerMap.put(code, handler);
    }

    public void handle(ChannelHandlerContext context, BinaryWebSocketFrame frame) {
        byte[] bytes = new byte[frame.content().readableBytes()];
        frame.content().readBytes(bytes);
        frame.content().release();

        ProtobufMsg protobufMsg = ProtobufMsg.unPack(bytes);

        ProtobufHandler handler = handlerMap.get(protobufMsg.getCode());
        if (handler == null) {
            return;
        } else {
            try {
                handler.handle(context, protobufMsg);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    public void handleChannelActive(ChannelHandlerContext context) {
        if (channelActiveHandler == null) {
            return;
        }
        channelActiveHandler.handle(context);
    }

    public void handleChannelInactive(ChannelHandlerContext context) {
        if (channelInactiveHandler == null) {
            return;
        }
        channelInactiveHandler.handle(context);
    }

    public void run() {
        Runnable exec = new Runnable() {
            @Override
            public void run() {
                start();
            }
        };
        exec.run();
    }

    public void start() {
        bossGroup = WebSocketUtil.getBossEventLoopGroup();
        workerGroup = WebSocketUtil.getWorkerEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new WebSocketServerChannelInitializer(sslContext, this))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_RCVBUF, 65536)
                .childOption(ChannelOption.SO_SNDBUF, 65536)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, Integer.valueOf(1000));

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void setChannelActiveHandler(ChannelStateHandler channelActiveHandler) {
        this.channelActiveHandler = channelActiveHandler;
    }

    public void setChannelInactiveHandler(ChannelStateHandler channelInactiveHandler) {
        this.channelInactiveHandler = channelInactiveHandler;
    }
}
