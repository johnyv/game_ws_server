package taurus.http.future;

import java.util.concurrent.ExecutorService;

public interface Future {
    static enum EventType{
        Complete, Exception
    }

    static enum FutureState {
        Pending, Running, Canceled, Error, Timeout, Success
    }

    public FutureState getState();

    public void setService(ExecutorService service);

    public void setState(FutureState status);

    public void cancel();

    public void addListener(FutureListener listener);

    public void fireEvent(EventType type, Object arg);

    public void success(Object arg);

    public void timeOut();

    public void exception(Throwable t);
}
