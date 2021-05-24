package netty;

import io.netty.handler.ssl.SslContext;
import netty.service.WebSocketService;
import session.SessionThread;

public class WebSocketBootstrap {
    private WebSocketService webSocketService;
    private static SessionThread sessionThread;

    public WebSocketBootstrap(String ip, int port, SslContext ssl) {
        webSocketService = new WebSocketService(ip,
                port,
                "ws_boss",
                "ws_worker",
                ssl);

        sessionThread = new SessionThread();
    }

    public void start(){
        webSocketService.start();
        sessionThread.start();
    }
}
