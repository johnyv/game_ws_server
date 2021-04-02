package bootstrap.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GameServerServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            GameServer server = GameServer.getInstance();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
    }
}
