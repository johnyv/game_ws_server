package http.client;

import http.http.Request;
import http.http.RequestFuture;

import java.net.InetSocketAddress;

public interface ClientPool {
    public void stop();

    public String getRemoteHost();

    public String getProtocol();

    public int getRemotePort();

    public InetSocketAddress getInetSocketAddress();

    public int getSize();

    public RequestFuture request(Request request);

    public void recycle(Client client);

    RequestFuture requestWithTimeOut(Request request, int time);

    public void schedule(Runnable task, int time);
}
