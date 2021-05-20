package service;

public interface IServerService {
    public boolean initialize();

    public void release();

    public boolean isRunning();

    public boolean startService() throws Exception;

    public boolean stopService() throws Exception;
}
