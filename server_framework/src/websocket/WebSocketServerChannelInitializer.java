package websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;


public class WebSocketServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private SSLContext sslContext;
    private WebSocketServer server;

    public WebSocketServerChannelInitializer(SSLContext sslContext, WebSocketServer server) {
        this.sslContext = sslContext;
        this.server = server;
    }

    private final static EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(8);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslContext != null) {
            //HTTPS
            SSLEngine sslEngine = sslContext.createSSLEngine();
            sslEngine.setUseClientMode(false);
            sslEngine.setNeedClientAuth(false);
            ch.pipeline().addLast(new SslHandler(sslEngine));
        }

        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast(businessGroup, "handler", new WebSocketServerHandler(server));
    }
}
