package bootstrap.server;

import common.constant.Loggers;
import org.slf4j.Logger;
import service.net.NetService;
import bootstrap.server.dispatcher.Dispatcher;
import bootstrap.server.loader.ProcessorLoader;
import service.processor.ProcessorService;

public class GameServer {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "443" : "8090"));

    private static Logger logger = Loggers.serverLogger;

    private static GameServer instance = new GameServer();

    protected NetService netService;

//    public static Dispatcher dispatcher = new Dispatcher();
    public static ProcessorLoader processorLoader;
    public static ProcessorService processorService;

    public static GameServer getInstance() {
        return instance;
    }

    public GameServer() {
        netService = new NetService();
    }

    private void start() throws Exception {
        netService.startup();

        processorLoader = new ProcessorLoader();
        processorLoader.load();

        processorService = new ProcessorService();
        processorService.start();
    }

    public void run() {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen(String ip, int port) throws Exception {
//        final SslContext sslCtx;
//        if (SSL) {
//            SelfSignedCertificate ssc = new SelfSignedCertificate();
//            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
//        } else {
//            sslCtx = null;
//        }
//
//        try {
//            bootstrap = new ServerBootstrap();
//            bootstrap.session(bossGroup, workerGroup)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .option(ChannelOption.SO_REUSEADDR, true)
//                    .childOption(ChannelOption.SO_REUSEADDR, true);
//
//            if (Epoll.isAvailable()) {
//                bootstrap.channel(EpollServerSocketChannel.class);
//            } else {
//                bootstrap.channel(NioServerSocketChannel.class);
//            }
//
//            bootstrap.childHandler(new WebSocketChannelInitializer(sslCtx));
//
//            InetSocketAddress address = new InetSocketAddress(ip, port);
//            Channel channel = bootstrap.bind(address).sync().channel();
//
//            logger.info("listen..." + address.getAddress().toString());
//            logger.info(String.format("port...%d", address.getPort()));
//            processorLoader = new ProcessorLoader(dispatcher);
//            processorLoader.load();
//
//            processorService = new ProcessorService();
//            processorService.start();
//
//            channel.closeFuture().sync();
//        } catch (InterruptedException e) {
//            logger.error("bind {}:{} failed", ip, port);
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
    }

    public void shutdown() {
//        try {
//            bossGroup.shutdownGracefully().sync();
//            workerGroup.shutdownGracefully().sync();
//        } catch (InterruptedException e) {
//
//        }
    }

    public static void main(String[] args) throws Exception {
        try {
            GameServer server = GameServer.getInstance();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
