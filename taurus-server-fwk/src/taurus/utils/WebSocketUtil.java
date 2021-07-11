package taurus.utils;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.Executors;

public class WebSocketUtil {
    private static NioEventLoopGroup bossEventLoopGroup;
    private static NioEventLoopGroup workerEventLoopGroup;

    public synchronized static void setBossEventLoopGroup(NioEventLoopGroup bossEventLoopGroup) {
        assert WebSocketUtil.bossEventLoopGroup == null;
        WebSocketUtil.bossEventLoopGroup = bossEventLoopGroup;
    }

    public synchronized static NioEventLoopGroup getBossEventLoopGroup() {
        if (bossEventLoopGroup == null) {
            bossEventLoopGroup = new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new DefaultThreadFactory("ws-boss-default", true)));
        }
        return bossEventLoopGroup;
    }

    public synchronized static void setWorkerEventLoopGroup(NioEventLoopGroup workerEventLoopGroup) {
        assert WebSocketUtil.workerEventLoopGroup == null;
        WebSocketUtil.workerEventLoopGroup = workerEventLoopGroup;
    }

    public synchronized static NioEventLoopGroup getWorkerEventLoopGroup() {
        if (workerEventLoopGroup == null) {
            workerEventLoopGroup = new NioEventLoopGroup(4, Executors.newFixedThreadPool(4, new DefaultThreadFactory("ws-worker-default", true)));
        }
        return workerEventLoopGroup;
    }
}
