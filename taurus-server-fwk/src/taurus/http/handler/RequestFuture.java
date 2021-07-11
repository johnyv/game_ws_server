package taurus.http.handler;

import taurus.http.client.Client;
import taurus.http.future.AbstractFuture;
import taurus.http.future.FutureListener;

public class RequestFuture extends AbstractFuture {
    private Request request;
    private Client client;
    private volatile Response response;
    private volatile Throwable t;

    public RequestFuture(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    @Override
    public void addListener(FutureListener listener) {
        if (this.getState().equals(FutureState.Success)) {
            for (FutureListener lis : this.getListeners()) {
                lis.complete(this.response);
            }
            listener.complete(this.response);
        } else {
            super.addListener(listener);
        }
    }

    public synchronized void cancel() {
        if (this.getState().equals(FutureState.Pending) || this.getState().equals(FutureState.Running)) {
            super.cancel();
            if (this.getState().equals(FutureState.Running)) {
                this.client.cancel();
            }
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public synchronized void success(Object arg) {
        super.success(arg);
        this.response = (Response) arg;
        this.notifyAll();
    }

    public synchronized void timeOut() {
        super.timeOut();
        this.t = new Exception("RequestFuture timeout");
        this.notifyAll();
    }

    public synchronized void exception(Throwable t) {
        super.exception(t);
        this.t = t;
        this.notifyAll();
    }

    public synchronized Response sync() throws Throwable {
        while (true) {
            if (this.response != null) {
                return response;
            }
            if (this.t != null) {
                throw t;
            }
            try {
                this.wait();
            } catch (InterruptedException e) {
                continue;
            }
        }
    }
}