package test;

import io.netty.channel.nio.NioEventLoopGroup;
import org.junit.Test;
import utils.HttpUtil;

import java.util.concurrent.Executors;

public class UnitTest {
    @Test
    public void test(){
        HttpUtil.setBossEventLoopGroup(new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new HttpClientTest.DefaultThreadFactory("http-boss"))));
        HttpUtil.setWorkerEventLoopGroup(new NioEventLoopGroup(4, Executors.newFixedThreadPool(4, new HttpClientTest.DefaultThreadFactory("http-worker"))));
        HttpUtil.setRequestEventLoopGroup(new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new HttpClientTest.DefaultThreadFactory("http-request"))));
        HttpUtil.initClientPool("http", "127.0.0.1", 8090, 100);
        HttpClientTest test = new HttpClientTest();
        try {
            test.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
