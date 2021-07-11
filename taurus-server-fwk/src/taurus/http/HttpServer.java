package taurus.http;

import taurus.http.handler.RequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import taurus.utils.HttpUtil;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpServer {
    public static void main(String[] args) throws Exception {
//        listen(8090, null).addHandler("/api", new RequestHandler() {
//            @Override
//            public void handle(ChannelHandlerContext context, HttpRequest request, String data) {
//                HttpUtil.httpResponse(context, request, data);
//            }
//        }).addHandler("/", new RequestHandler() {
//            @Override
//            public void handle(ChannelHandlerContext context, HttpRequest request, String data) {
//                HttpUtil.httpResponse(context, request, "unknown");
//            }
//        });
        HttpServer httpServer = HttpServer.listen(8090);
        httpServer.addHandler("/api", new RequestHandler() {
            @Override
            public void handle(ChannelHandlerContext context, HttpRequest request, String data) {
                log.info("handle.../api");
                HttpUtil.httpResponse(context, request, data);
            }
        });
        httpServer.addHandler("/", new RequestHandler() {
            @Override
            public void handle(ChannelHandlerContext context, HttpRequest request, String data) {
                log.info("handle.../");
                HttpUtil.httpResponse(context, request, "data");
            }
        });
        httpServer.start(8090, null);
    }

    private volatile static Map<Integer, HttpServer> serverMap = new HashMap<Integer, HttpServer>();

    public static HttpServer listen(int port) throws Exception {
        return listen(port, null);
    }

    public static HttpServer getServer(int port) {
        return serverMap.get(port);
    }

    public static HttpServer listen(int port, SSLContext ssl) throws Exception {
        if (serverMap.containsKey(port)) {
            throw new Exception("HttpServer::listen port occupied...");
        }
        HttpServer server = new HttpServer();
//        server.start(port, ssl);
        serverMap.put(port, server);
        return server;
    }

    public static void stop(int port) {
        HttpServer server = serverMap.get(port);
        if (server != null) {
            server.stop();
        }
    }

    public void stop() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        serverMap.remove(port);
    }

    private volatile EventLoopGroup workerGroup;
    private volatile EventLoopGroup bossGroup;
    private volatile ServerBootstrap bootstrap;

    private volatile int port;
    private volatile Map<String, RequestHandler> handlerMap = new HashMap<String, RequestHandler>();

    public void request(ChannelHandlerContext context, HttpRequest request, String data) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
        URI uri = new URI(request.uri());
        RequestHandler handler = handlerMap.get(uri.getPath());
        if (handler == null) {
            HttpUtil.httpResponse(context, request, "invalid request!");
        } else {
            try {
                handler.handle(context, request, data);
            } catch (Exception e) {
                HttpUtil.httpResponse(context, request, "internal handler error!");
                log.error(e.getMessage());
            }
        }
    }

    public HttpServer addHandler(String uri, RequestHandler handler) {
        handlerMap.put(uri, handler);
        return this;
    }

    public void start(final int port, final SSLContext sslContext) {
        this.port = port;
        bossGroup = HttpUtil.getBossEventLoopGroup();
        workerGroup = HttpUtil.getWorkerEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HttpServerChannelInitializer(sslContext, this))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
