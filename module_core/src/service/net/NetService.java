package service.net;

import common.constant.Loggers;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import org.slf4j.Logger;
import service.IService;
import service.net.netty.NettyServerService;
import service.net.websocket.WebSocketChannelInitializer;

public class NetService implements IService {
    private final Logger logger = Loggers.serverLogger;

    private NettyServerService webSocketServerService;

    private SslContext sslContext;

    private ChannelInitializer<SocketChannel> webSocketChannelInitialer;

    public NetService() {
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void startup() throws Exception {
        webSocketChannelInitialer = new WebSocketChannelInitializer(sslContext);

        webSocketServerService = new NettyServerService("127.0.0.1", 8090, "net_tcp_boss", "net_tcp_worker", webSocketChannelInitialer);
        webSocketServerService.startService();
    }

    @Override
    public void shutdown() throws Exception {
        webSocketServerService.stopService();
    }
}
