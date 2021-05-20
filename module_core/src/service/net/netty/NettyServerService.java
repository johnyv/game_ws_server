package service.net.netty;

import common.constant.Loggers;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import service.IServerService;
import common.ThreadNameFactory;
import service.manager.ServiceManager;

import java.net.InetSocketAddress;

public class NettyServerService implements IServerService {
    private final Logger logger = Loggers.serverLogger;
    public static final String SERVICE_ID = "NETTY_SERVER_SERVICE";

    private int serverPort;
    private InetSocketAddress serverAddr;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final ThreadNameFactory bossThreadNameFactory;
    private final ThreadNameFactory workerThreadNameFactory;
    private final ChannelInitializer channelInitializer;

    private ChannelFuture serverChannelFuture;

    public NettyServerService(String ip, int port, String bossTreadName, String workThreadName, ChannelInitializer initializer) {
        serverPort = port;
        serverAddr = new InetSocketAddress(ip, port);
        bossThreadNameFactory = new ThreadNameFactory(bossTreadName);
        workerThreadNameFactory = new ThreadNameFactory(workThreadName);
        channelInitializer = initializer;
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

    @Override
    public boolean initialize() {
        ServiceManager.getInstance().register(SERVICE_ID, this);
        return true;
    }

    @Override
    public void release() {
        ServiceManager.getInstance().remove(SERVICE_ID);
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public boolean startService() throws Exception {
        boolean serviceFlag = true;
        bossGroup = createBossGroup();//new NioEventLoopGroup(1, bossThreadNameFactory);
        workerGroup = createWorkerGroup();//new NioEventLoopGroup(0, workerThreadNameFactory);
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
                    .childHandler(channelInitializer);

            serverChannelFuture = serverBootstrap.bind(serverAddr).sync();

            serverChannelFuture.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            serviceFlag = false;
        }
        return serviceFlag;
    }

    @Override
    public boolean stopService() throws Exception {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        return true;
    }
}
