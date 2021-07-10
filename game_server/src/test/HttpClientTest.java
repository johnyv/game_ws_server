package test;

import http.future.FutureListener;
import http.http.Response;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import utils.HttpUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class HttpClientTest {
    /**
     * The default thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(0);
        private final String namePrefix;

        DefaultThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = name + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.incrementAndGet(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

//    public static void main(String args[]) throws Throwable {
//        HttpUtil.setBossEventLoopGroup(new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new DefaultThreadFactory("http-boss"))));
//        HttpUtil.setWorkerEventLoopGroup(new NioEventLoopGroup(4, Executors.newFixedThreadPool(4, new DefaultThreadFactory("http-worker"))));
//        HttpUtil.setRequestEventLoopGroup(new NioEventLoopGroup(2, Executors.newFixedThreadPool(2, new DefaultThreadFactory("http-request"))));
//        HttpUtil.initClientPool("http", "127.0.0.1", 19043, 100);
//        HttpClientTest test = new HttpClientTest();
//        test.start();
//    }

    private long time;
    private int runCount = 1;	//累计测试请求数量
    private int concurrent = 8000;		//每秒并发请求数量
    private AtomicInteger finishCount = new AtomicInteger(0);
    private AtomicInteger errorCount = new AtomicInteger(0);

    public void start() throws InterruptedException {
        time = System.currentTimeMillis();
        finishCount.set(0);
        errorCount.set(0);
        int count = 0;

        time = System.currentTimeMillis();
        while (count<runCount) {
            int childCount = 0;
            while(childCount<concurrent && count<runCount) {
                count++;
                childCount++;
                HttpUtil.postRequest(
                        "http://127.0.0.1:8090/api?method="+count,
                        "post="+count,
                        new FutureListener() {
                            @Override
                            public void complete(Object arg) {
                                int finish = finishCount.incrementAndGet();
                                int error = errorCount.get();
                                Response response = (Response)arg;
                                String result = response.getResult();
                                log.info(result.substring(1, Math.min(result.length(), 50)) + ", " + (System.currentTimeMillis()-time) + ", finish=" + finish + ", error=" + error + ", total=" + (error+finish));
                                if(finish+error>=runCount) {
                                    log.info("异步请求总耗时：" + (System.currentTimeMillis()-time));
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                    }
                                    System.exit(0);
                                }
                            }

                            @Override
                            public void exception(Throwable t) {
                                int error = errorCount.incrementAndGet();
                                int finish = finishCount.get();
                                log.error(t.getMessage() + ", " + (System.currentTimeMillis()-time) + ", finish=" + finish + ", error=" + error + ", total=" + (error+finish));
                                if(finish+error>=runCount) {
                                    log.info("异步请求总耗时：" + (System.currentTimeMillis()-time));
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                    }
                                    System.exit(0);
                                }
                            }
                        }
                );
            }
            Thread.sleep(1000);
        }
    }
}
