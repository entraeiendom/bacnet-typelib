package no.entra.bacnet.services;

import java.util.ArrayList;
import java.util.List;

public class ServicesSupported {

    private final List<ServiceType> supportedServices;

    public ServicesSupported() {
        supportedServices = new ArrayList<>();
    }

    public ServicesSupported(List<ServiceType> supportedServices) {
        this.supportedServices = supportedServices;
    }
    public void addSupportedService(ServiceType serviceType) {
        supportedServices.add(serviceType);
    }
    public void removeSupportedService(ServiceType serviceType) {
        if (isServiceSupported(serviceType)) {
            supportedServices.remove(serviceType);
        }
    }
    public boolean isServiceSupported(ServiceType serviceType) {
        return supportedServices.contains(serviceType);
    }
}
