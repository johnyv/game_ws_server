import lombok.extern.slf4j.Slf4j;
import websocket.WebSocketServer;
import websocket.handler.ChannelStateHandler;
import websocket.handler.ProtobufHandler;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class GameServer {
    private SSLContext sslContext;

    private static WebSocketServer webSocketServer = null;

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "443" : "8090"));

    private static GameServer instance = new GameServer();

    public static GameServer getInstance() {
        return instance;
    }

    public GameServer() {
        sslContext = null;
    }

    public void start() {
        webSocketServer = new WebSocketServer(8090, sslContext);
        addHandlers(webSocketServer);
        webSocketServer.run();
//        addHandlers(webSocketServer);
    }

    public void run() {
//        try {
//            start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        addHandlers();
        start();
    }

    private void addHandlers(WebSocketServer server) {
        String handlerFile = "resources/handler.properties";
        ClassLoader loader = getClass().getClassLoader();

        Properties properties = new Properties();
        InputStream inputStream = loader.getResourceAsStream(handlerFile);

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<Map.Entry<Object, Object>> es = properties.entrySet();
        Iterator<Map.Entry<Object, Object>> it = es.iterator();

        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            int code = Integer.parseInt((String) entry.getKey());
            String handler = (String) entry.getValue();

            try {
                Object obj = loader.loadClass(handler).newInstance();
                server.addHandler(code, ProtobufHandler.class.cast(obj));
                log.info(handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Object obj = loader.loadClass("handler.ChannelActiveHandler").newInstance();
            server.setChannelActiveHandler(ChannelStateHandler.class.cast(obj));
            obj = loader.loadClass("handler.ChannelInactiveHandler").newInstance();
            server.setChannelInactiveHandler(ChannelStateHandler.class.cast(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        GameServer server = GameServer.getInstance();
        server.start();
//        try {
//            GameServer server = GameServer.getInstance();
//            server.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
