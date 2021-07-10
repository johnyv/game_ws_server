import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GameServerServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        GameServer server = GameServer.getInstance();
        server.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
    }
}
