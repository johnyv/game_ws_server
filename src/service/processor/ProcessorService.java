package service.processor;

import group.ClientCenter;

public class ProcessorService implements Runnable {
    private Thread serviceThread;
    private volatile boolean running;

    @Override
    public void run() {
        while (running) {
            try {
                ClientCenter.INSTANCE.processAll();
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
