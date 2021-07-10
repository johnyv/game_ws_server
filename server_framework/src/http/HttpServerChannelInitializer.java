package http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class HttpServerChannelInitializer extends ChannelInitializer<Channel> {
    private SSLContext sslContext;
    private HttpServer server;

    public HttpServerChannelInitializer(SSLContext sslContext, HttpServer server) {
        this.sslContext = sslContext;
        this.server = server;
    }

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

        ch.pipeline().addLast(new HttpObjectAggregator(1024 * 1024 * 2));
        ch.pipeline().addLast(new HttpRequestDecoder(1024 * 1024 * 2, 8192, 8192));

        ch.pipeline().addLast(new HttpResponseEncoder());
        ch.pipeline().addLast(new HttpServerHandler(server));
    }
}
