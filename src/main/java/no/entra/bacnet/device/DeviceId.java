package no.entra.bacnet.device;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;

public class DeviceId extends ObjectId {
    public DeviceId(Integer instanceNumber) {
        super(ObjectType.Device, instanceNumber);
    }

}
