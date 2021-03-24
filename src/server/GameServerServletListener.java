package server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GameServerServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            GameServer server = GameServer.getInstance();
            server.listen("127.0.0.1", 8090);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
    }
}
