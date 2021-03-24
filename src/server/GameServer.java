package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
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
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class GameServer {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "443" : "8090"));

    private static Logger logger = LoggerFactory.getLogger(GameServer.class);

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;

    private static GameServer instance = new GameServer();

    public GameServer() {
        bossGroup = buildGroup();//new NioEventLoopGroup();
        workerGroup = buildGroup();//new NioEventLoopGroup(4);
//        bootstrap = new ServerBootstrap();
//        bootstrap.group(bossGroup, workerGroup)
//                .channel(NioServerSocketChannel.class)
//                .option(ChannelOption.SO_BACKLOG, 5)
//                .childOption(ChannelOption.TCP_NODELAY, true);
    }

    public static GameServer getInstance() {
        return instance;
    }

    public void listen(String ip, int port) throws Exception {
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true);

            if (Epoll.isAvailable()) {
                bootstrap.channel(EpollServerSocketChannel.class);
            } else {
                bootstrap.channel(NioServerSocketChannel.class);
            }

            bootstrap.childHandler(new GameServerInitializer(sslCtx));

            InetSocketAddress address = new InetSocketAddress(ip, port);
            Channel channel = bootstrap.bind(address).sync().channel();

            logger.info("listen..." + address.getAddress().toString());
            logger.info(String.format("port...%d", address.getPort()));

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("bind {}:{} failed", ip, port);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void shutdown() {
        try {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) throws Exception {
        try {
            GameServer server = GameServer.getInstance();
            server.listen("127.0.0.1", PORT);
//            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int run() throws Exception {
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            System.out.println("liteServer starting..." + GameServer.PORT);
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new GameServerInitializer(sslCtx))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            Channel ch = b.bind(PORT).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        return 0;
    }

    private EventLoopGroup buildGroup() {
        if (Epoll.isAvailable()) {
            return new EpollEventLoopGroup();
        }

        return new NioEventLoopGroup();
    }

//    private EventLoopGroup buildGroup(int nThreads) {
//        if (Epoll.isAvailable()) {
//            return new EpollEventLoopGroup(nThreads);
//        }
//
//        return new NioEventLoopGroup(nThreads);
//    }
}
