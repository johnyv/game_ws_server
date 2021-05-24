package netty.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import netty.websocket.WebSocketInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ThreadNameFactory;

import java.net.InetSocketAddress;

public class WebSocketService {
    private final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private int serverPort;
    private InetSocketAddress serverAddr;
    private SslContext sslContext;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final ThreadNameFactory bossThreadNameFactory;
    private final ThreadNameFactory workerThreadNameFactory;

    private ChannelFuture serverChannelFuture;

    public WebSocketService(String ip, int port, String bossTreadName, String workThreadName, SslContext ssl) {
        serverPort = port;
        serverAddr = new InetSocketAddress(ip, port);
        sslContext = ssl;
        bossThreadNameFactory = new ThreadNameFactory(bossTreadName);
        workerThreadNameFactory = new ThreadNameFactory(workThreadName);
    }

    private EventLoopGroup createBossGroup() {
        if (Epoll.isAvailable()) {
            return new EpollEventLoopGroup(1, bossThreadNameFactory);
        }

        return new NioEventLoopGroup(1, bossThreadNameFactory);
    }

    private EventLoopGroup createWorkerGroup() {
        if (Epoll.isAvailable()) {
            return new EpollEventLoopGroup(0, workerThreadNameFactory);
        }

        return new NioEventLoopGroup(0, workerThreadNameFactory);
    }


    public boolean start() {
        boolean serviceFlag = true;
        bossGroup = createBossGroup();
        workerGroup = createWorkerGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap = serverBootstrap.group(bossGroup, workerGroup);

            if (Epoll.isAvailable()) {
                serverBootstrap.channel(EpollServerSocketChannel.class);
            } else {
                serverBootstrap.channel(NioServerSocketChannel.class);
            }

            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_RCVBUF, 65536)
                    .childOption(ChannelOption.SO_SNDBUF, 65536)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, Integer.valueOf(1000))
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketInitializer(sslContext));

            serverChannelFuture = serverBootstrap.bind(serverAddr).sync();

            serverChannelFuture.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            serviceFlag = false;
        }
        return serviceFlag;
    }

    public boolean stop() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        return true;
    }
}
