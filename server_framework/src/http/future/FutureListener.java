package http.future;

public interface FutureListener {
    public void complete(Object arg);

    public void exception(Throwable t);
}
