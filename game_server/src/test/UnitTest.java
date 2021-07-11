package test;

import io.netty.channel.nio.NioEventLoopGroup;
import org.junit.Test;
import taurus.utils.HttpUtil;

import java.util.concurrent.Executors;

public class UnitTest {
    @Test
    public void test(){
        HttpUtil.setBossEventLoopGroup(new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new HttpClientTest.DefaultThreadFactory("handler-boss"))));
        HttpUtil.setWorkerEventLoopGroup(new NioEventLoopGroup(4, Executors.newFixedThreadPool(4, new HttpClientTest.DefaultThreadFactory("handler-worker"))));
        HttpUtil.setRequestEventLoopGroup(new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new HttpClientTest.DefaultThreadFactory("handler-request"))));
        HttpUtil.initClientPool("taurus/http", "127.0.0.1", 8090, 100);
        HttpClientTest test = new HttpClientTest();
        try {
            test.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
