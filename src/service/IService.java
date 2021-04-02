package service;

public interface IService {
    public String getId();
    public void startup() throws Exception;
    public void shutdown() throws Exception;
}
