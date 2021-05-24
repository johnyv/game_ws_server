import io.netty.handler.ssl.SslContext;
import loader.ProcessorLoader;
import netty.WebSocketBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameServer {
    //    private SslContext sslContext;
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "443" : "8090"));

    private static Logger logger = LoggerFactory.getLogger(GameServer.class);

    private static GameServer instance = new GameServer();

    private WebSocketBootstrap webSocketBootstrap;
    public static ProcessorLoader processorLoader;

    public static GameServer getInstance() {
        return instance;
    }

    public GameServer() {
        SslContext sslContext = null;

        webSocketBootstrap = new WebSocketBootstrap("127.0.0.1",
                8090,
                sslContext);
    }

    private void start() throws Exception {

        webSocketBootstrap.start();

        processorLoader = new ProcessorLoader();
        processorLoader.load();
    }

    public void run() {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            GameServer server = GameServer.getInstance();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
