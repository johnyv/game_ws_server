package service.manager;

import service.IServerService;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private static ServiceManager instance = new ServiceManager();
    private final Map<String, IServerService> services = new HashMap<String, IServerService>();

    public static ServiceManager getInstance() {
        return instance;
    }

    public final void register(String serviceId, IServerService service) {
        services.put(serviceId, service);
    }

    public final IServerService get(String serviceId) {
        return services.get(serviceId);
    }

    public final IServerService remove(String serviceId) {
        return services.remove(serviceId);
    }
}