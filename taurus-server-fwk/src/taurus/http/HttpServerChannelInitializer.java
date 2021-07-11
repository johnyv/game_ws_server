package taurus.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class HttpServerChannelInitializer extends ChannelInitializer<Channel> {
    private SSLContext sslContext;
    private HttpServer server;

    public HttpServerChannelInitializer(SSLContext sslContext, HttpServer server) {
        this.sslContext = sslContext;
        this.server = server;
    }

    private final static EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(8);

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        if (sslContext != null) {
            //HTTPS
            SSLEngine sslEngine = sslContext.createSSLEngine();
            sslEngine.setUseClientMode(false);
            sslEngine.setNeedClientAuth(false);
            ch.pipeline().addLast(new SslHandler(sslEngine));
        }

        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 2));
        ch.pipeline().addLast("request-codec", new HttpRequestDecoder(1024 * 1024 * 2, 8192, 8192));

        ch.pipeline().addLast("response-encoder", new HttpResponseEncoder());
        ch.pipeline().addLast(businessGroup, "handler", new HttpServerHandler(server));
    }
}
