package taurus.http.future;

import lombok.extern.slf4j.Slf4j;
import taurus.utils.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public abstract class AbstractFuture implements Future {
    private ReentrantLock tlock = new ReentrantLock();
    private List<FutureListener> listeners = new ArrayList<FutureListener>(2);

    private volatile FutureState status = FutureState.Pending;
    private volatile ExecutorService service;

    public synchronized void setService(ExecutorService service) {
        this.service = service;
    }

    public synchronized ExecutorService getService() {
        if (service == null) {
            return HttpUtil.getWorkerEventLoopGroup();
        }
        return service;
    }

    @Override
    public void addListener(FutureListener listener) {
        if (this.status.equals(FutureState.Timeout)) {
            listener.exception(new Exception("timout"));
        } else if (this.status.equals(FutureState.Error)) {
            listener.exception(new Exception("an exception has occurred"));
        } else if (this.status.equals(FutureState.Success)) {
            listener.complete(null);
        } else if (this.status.equals(FutureState.Canceled)) {
            listener.exception(new Exception("exception cancellation"));
        } else {
            this.tlock.lock();
            try {
                this.listeners.add(listener);
            } finally {
                this.tlock.unlock();
            }
        }
    }

    @Override
    public void fireEvent(EventType type, Object arg) {

        if (type.equals(EventType.Complete)) {
            this.status = FutureState.Success;
            this.tlock.lock();
            try {
                for (FutureListener lis : this.listeners) {
                    lis.complete(arg);
                }
            } finally {
                this.tlock.unlock();
            }
        } else if (type.equals(EventType.Exception)) {
            this.status = FutureState.Error;
            this.tlock.lock();
            try {
                for (FutureListener lis : this.listeners) {
                    lis.exception((Throwable) arg);
                }
            } finally {
                this.tlock.unlock();
            }
        }
    }

    @Override
    public void cancel() {
        this.status = FutureState.Canceled;
    }


    public synchronized List<FutureListener> getListeners() {
        return listeners;
    }

    @Override
    public synchronized FutureState getState() {
        return this.status;
    }

    @Override
    public synchronized void setState(FutureState status) {
        this.status = status;
    }


    @Override
    public synchronized void timeOut() {
        if (getState().equals(FutureState.Pending) || getState().equals(FutureState.Running)) {
            if (this.getState().equals(FutureState.Running)) {
                this.cancel();
            }
            this.setState(FutureState.Timeout);
            getService().execute(new Runnable() {
                @Override
                public void run() {
                    AbstractFuture.this.fireEvent(EventType.Exception, new Exception("AbstractFuture timeout"));
                }
            });
        }
    }

    @Override
    public synchronized void exception(final Throwable t) {
        if (this.status.equals(FutureState.Running)) {
            this.setState(FutureState.Error);
            getService().execute(new Runnable() {
                @Override
                public void run() {
                    AbstractFuture.this.fireEvent(EventType.Exception, t);
                }
            });
        }
    }

    @Override
    public synchronized void success(final Object arg) {
        if (this.getState().equals(FutureState.Running)) {
            this.setState(FutureState.Success);
            getService().execute(new Runnable() {
                @Override
                public void run() {
                    AbstractFuture.this.fireEvent(EventType.Complete, arg);
                }
            });
        } else {
            log.warn("finished, incorrect state");
        }
    }
}
