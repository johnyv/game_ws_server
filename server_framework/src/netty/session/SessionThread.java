package netty.session;

public class SessionThread implements Runnable {
    private Thread serviceThread;
    private volatile boolean running;

    @Override
    public void run() {
        while (running) {
            try {
                SessionManager.getInstance().processAll();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        running = true;
        serviceThread = new Thread(this);
        serviceThread.start();
    }

    public void stop() {
        running = false;
    }
}
