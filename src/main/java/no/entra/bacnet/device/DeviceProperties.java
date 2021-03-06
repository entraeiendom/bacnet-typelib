package no.entra.bacnet.device;

import no.entra.bacnet.internal.objects.ObjectProperties;
import no.entra.bacnet.internal.properties.PropertyIdentifier;
import no.entra.bacnet.objects.ObjectId;

import java.util.Set;

import static no.entra.bacnet.objects.ObjectType.Device;

public class DeviceProperties extends ObjectProperties {
    public DeviceProperties(int instanceNumber, Set<PropertyIdentifier> propertyIdentifiers) {
        super(new ObjectId(Device, instanceNumber), propertyIdentifiers);
    }
}
