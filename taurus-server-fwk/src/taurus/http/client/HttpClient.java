package taurus.http.client;

import taurus.http.handler.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class HttpClient implements Client {
    private long id;
    private ClientPool pool;
    private Request request;
    private ClientState status;
    private NioEventLoopGroup group;
    private Channel channel;

    public static AtomicLong activeClient = new AtomicLong(0);
    public static SslContext sslContext;

    public HttpClient(ClientPool pool) {
        this.pool = pool;
        this.status = ClientState.Starting;
        this.id = activeClient.incrementAndGet();
        log.info("HttpClient: " + id);
        if (sslContext == null) {
            try {
                sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } catch (SSLException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public long getId() {
        return id;
    }

    public void request() {
        if (this.request == null) {
            return;
        }
        if (this.channel == null || !this.channel.isActive()) {
            start();
            return;
        }
        this.setStatus(ClientState.Working);
        QueryStringEncoder encoder = new QueryStringEncoder(request.getPath());
        URI uriGet = null;
        try {
            uriGet = new URI(encoder.toString());
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
        FullHttpRequest req = null;
        if (request.getMethod().equals(Request.RequestMethod.GET)) {
            req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uriGet.toASCIIString());
            req.headers().add("Upgrade-Insecure-Requests", 1);
            if (request.getBody() != null) {
                req.content().writeBytes(request.getBody());
                req.headers().add("content-length", req.content().readableBytes());
                request.getBody().release();
                request.setBody(null);
            }
        } else if (request.getMethod().equals(Request.RequestMethod.POST)) {
            req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uriGet.toASCIIString());
            req.headers().add("Upgrade-Insecure-Requests", 1);
            if (request.getBody() != null) {
                req.content().writeBytes(request.getBody());
                req.headers().add("content-length", req.content().readableBytes());
                request.getBody().release();
                request.setBody(null);
            }
        }

        for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
            req.headers().add(header.getKey(), header.getValue());
        }

        req.headers().add("Host", pool.getRemoteHost());
        req.headers().add("Connection", "keep-alive");
        this.channel.pipeline().write(req);
        this.channel.pipeline().flush();
    }

    private void start() {
        if (channel != null) {
            channel.close();
            channel = null;
        }
        setStatus(ClientState.Starting);
//		this.channel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        Bootstrap bootstrap = new Bootstrap();
        if (pool.getProtocol().equalsIgnoreCase("https")) {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitializer(this, sslContext));
        } else {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitializer(this, null));
        }
        ChannelFuture future = bootstrap.connect(pool.getInetSocketAddress());
        channel = future.channel();
        future.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    public synchronized void exception(Throwable t) {
        if (status.equals(ClientState.Stopped)) {
            return;
        }
        if (request != null) {
            request.getFuture().exception(t);
        }
        this.stop();
        this.getClientPool().recycle(this);
    }

    public synchronized void disconnected() {
        if (status.equals(ClientState.Stopped)) {
            return;
        }
        if (request != null) {
            request.getFuture().exception(new Exception("disconnected"));
        }
        this.stop();
        this.getClientPool().recycle(this);
    }

    public Channel getChannel() {
        return channel;
    }

    public void setGroup(NioEventLoopGroup group) {
        this.group = group;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return this.request;
    }

    public ClientPool getClientPool() {
        return this.pool;
    }

    public void cancel() {
        if (this.getStatus().equals(ClientState.Working)) {
            if (this.channel != null) {
                this.channel.close();
                this.channel = null;
            }
        } else {
            this.request = null;
        }
    }

    public void stop() {
        this.setStatus(ClientState.Stopped);
        if (this.channel != null) {
            this.channel.close();
            this.channel = null;
        }
        this.request = null;
    }

    public ClientState getStatus() {
        return this.status;
    }

    public void setStatus(ClientState status) {
        this.status = status;
    }

    public synchronized void ready() {
        if (this.status.equals(ClientState.Starting)) {
            if (this.request != null) {
                this.request();
            } else {
                this.setStatus(ClientState.Ready);
                this.setRequest(null);
                this.getClientPool().recycle(this);
            }
        } else if (this.status.equals(ClientState.Working)) {
            this.setStatus(ClientState.Ready);
            this.setRequest(null);
            this.getClientPool().recycle(this);
        } else {
            log.error("incorrect client state, retrieve directly:" + getStatus());
            this.stop();
            this.getClientPool().recycle(this);
        }
    }
}