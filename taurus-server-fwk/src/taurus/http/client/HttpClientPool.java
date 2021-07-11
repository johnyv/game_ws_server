package taurus.http.client;

import taurus.http.client.Client.ClientState;
import taurus.http.future.Future;
import taurus.http.handler.Request;
import taurus.http.handler.RequestFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class HttpClientPool implements ClientPool {
    private int size;
    private String remoteHost;
    private String protocol;
    private int remotePort = 80;
    private AtomicInteger activeClient = new AtomicInteger(0);
    private LinkedBlockingQueue<Client> clients;
    private NioEventLoopGroup nioEventLoopGroup;
    private InetSocketAddress remoteAddr;

    public HttpClientPool(int size, String protocol, String remoteHost, NioEventLoopGroup nioEventLoopGroup) {
        this.size = size;
        this.nioEventLoopGroup = nioEventLoopGroup;
        this.remoteHost = remoteHost;
        this.protocol = protocol;
        this.clients = new LinkedBlockingQueue<Client>(size);
        this.remoteAddr = InetSocketAddress.createUnresolved(remoteHost, remotePort);
    }

    public HttpClientPool(int size, String protocol, String remoteHost, int remotePort, NioEventLoopGroup nioEventLoopGroup) {
        this.size = size;
        this.nioEventLoopGroup = nioEventLoopGroup;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.protocol = protocol;
        this.clients = new LinkedBlockingQueue<Client>(this.size);
        this.remoteAddr = InetSocketAddress.createUnresolved(remoteHost, remotePort);
    }

    public Client newClient() {
        HttpClient client = new HttpClient(this);
        client.setGroup(nioEventLoopGroup);
        return client;
    }

    @Override
    public void schedule(Runnable task, int time) {
        nioEventLoopGroup.schedule(task, time, TimeUnit.MILLISECONDS);
    }

    public NioEventLoopGroup getNioEventLoopGroup() {
        return nioEventLoopGroup;
    }

    @Override
    public void stop() {
        nioEventLoopGroup.shutdownGracefully();
    }

    @Override
    public String getRemoteHost() {
        return this.remoteHost;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public InetSocketAddress getInetSocketAddress() {
        return remoteAddr;
    }

    @Override
    public int getRemotePort() {
        return this.remotePort;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public RequestFuture request(Request request) {
        Client client = clients.poll();
        if (client == null) {
            int count = activeClient.incrementAndGet();
            if (count <= size) {
                client = newClient();
            } else {
                activeClient.decrementAndGet();
                try {
                    client = clients.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        RequestFuture future = new RequestFuture(request);
        request.setFuture(future);
        future.setClient(client);
        client.setRequest(request);
        future.setState(Future.FutureState.Running);
        return future;
    }

    @Override
    public RequestFuture requestWithTimeOut(Request request, int time) {
        long before = System.currentTimeMillis();
        final RequestFuture future = new RequestFuture(request);
        Client client = null;
        while (true) {
            try {
                client = this.clients.poll(time, TimeUnit.MILLISECONDS);
                break;
            } catch (InterruptedException e) {
                continue;
            }

        }
        if (client == null) {
            future.timeOut();
            return future;
        }
        long after = System.currentTimeMillis();

        future.setClient(client);
        client.setRequest(request);
        request.setFuture(future);
        future.setState(Future.FutureState.Running);

        time = (int) (time - (after - before));
        this.schedule(new Runnable() {
            @Override
            public void run() {
                future.timeOut();
            }
        }, time);
        client.request();
        return future;
    }

    @Override
    public void recycle(Client client) {
        if (client.getStatus() == ClientState.Stopped) {
            if (clients.contains(client)) {
                clients.remove(client);
            }
            log.warn("HttpClient[id=" + client.getId() + "] destroyed");
            client.setStatus(ClientState.Recycled);

            clients.add(newClient());
        } else {
            client.setStatus(ClientState.Recycled);
            clients.add(client);
        }
    }
}