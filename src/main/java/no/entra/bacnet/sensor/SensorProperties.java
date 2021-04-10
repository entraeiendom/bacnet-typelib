package no.entra.bacnet.sensor;

import no.entra.bacnet.internal.objects.ObjectProperties;
import no.entra.bacnet.properties.PropertyIdentifier;

import java.util.Set;

public class SensorProperties extends ObjectProperties {
    public SensorProperties(SensorId sensorId, Set<PropertyIdentifier> propertyIdentifiers) {
        super(sensorId, propertyIdentifiers);
    }
}
