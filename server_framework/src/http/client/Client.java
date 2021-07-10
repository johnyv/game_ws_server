package http.client;

import http.http.Request;

public interface Client {
    public static enum ClientState {
        Starting, Ready, Working, Stopped, Recycled
    }

    public long getId();

    public ClientPool getClientPool();

    public void request();

    public void stop();

    public void cancel();

    public void setRequest(Request request);

    public Request getRequest();

    public ClientState getStatus();

    public void setStatus(ClientState status);

    public void ready();
}
