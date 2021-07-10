package http.client;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslContext;
    private final HttpClient client;

    public HttpClientInitializer(HttpClient client, SslContext sslContext) {
        this.sslContext = sslContext;
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipe = ch.pipeline();
        // Enable HTTPS if necessary.
        if (sslContext != null) {
            SSLEngine sslEngine = sslContext.newEngine(ByteBufAllocator.DEFAULT);
            sslEngine.setUseClientMode(true);
            pipe.addLast(new SslHandler(sslEngine));
        }

        pipe.addLast(new HttpClientCodec());

        // Remove the following line if you don't want automatic content decompression.
        // pipe.addLast(new HttpContentDecompressor());

        // Uncomment the following line if you don't want to handle HttpContents.
        // pipe.addLast(new HttpObjectAggregator(1048576));

        pipe.addLast(new HttpClientHandler(client));
    }
}
