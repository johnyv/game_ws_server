package server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import server.handler.WebSocketHandler;


public class GameServerInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;

    public GameServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
        }

//        pipeline.addLast("httpServerCodec", new HttpServerCodec());
//        pipeline.addLast(new HttpObjectAggregator(65535));
//
//        pipeline.addLast(new WebSocketServerCompressionHandler());
//        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
//        pipeline.addLast(new HelloServerHandler());
//        pipeline.addLast(new HttpServerExpectContinueHandler());
//        pipeline.addLast(new HttpServerHandler());

        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
//        pipeline.addLast("encoder", new ProtoEncoder());
//        pipeline.addLast("decoder", new ProtoDecoder());
        pipeline.addLast(new WebSocketHandler("/wssLite"));
    }
}
