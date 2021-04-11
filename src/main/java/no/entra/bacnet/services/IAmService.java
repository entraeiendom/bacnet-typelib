package no.entra.bacnet.services;

import no.entra.bacnet.internal.objects.Segmentation;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.vendor.Vendor;

public class IAmService implements Service {
    private ObjectId objectId;
    private Integer maxADPULengthAccepted;
    private Segmentation segmentation;
    private Vendor vendor;

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }


    public void setMaxADPULengthAccepted(Integer maxADPULengthAccepted) {
        this.maxADPULengthAccepted = maxADPULengthAccepted;
    }

    public Integer getMaxADPULengthAccepted() {
        return maxADPULengthAccepted;
    }

    public void setSegmentation(Segmentation segmentation) {
        this.segmentation = segmentation;
    }

    public Segmentation getSegmentation() {
        return segmentation;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Vendor getVendor() {
        return vendor;
    }
}
