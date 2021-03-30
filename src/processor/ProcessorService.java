package processor;

import group.GroupManager;

public class ProcessorService implements Runnable {
    private Thread serviceThread;
    private volatile boolean running;

    @Override
    public void run() {
        while (running) {
            try {
                GroupManager.INSTANCE.processAll();
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
