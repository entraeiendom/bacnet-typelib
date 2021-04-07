package no.entra.bacnet.objects;

import no.entra.bacnet.properties.PropertyIdentifier;

import java.util.Map;
import java.util.Set;

public class ObjectProperties {
    private final ObjectId objectId;
    private final Set<PropertyIdentifier> propertyIdentifiers;
    private Map<PropertyIdentifier, Object> properties;

    public ObjectProperties(ObjectId objectId, Set<PropertyIdentifier> propertyIdentifiers) {
        this.objectId = objectId;
        this.propertyIdentifiers = propertyIdentifiers;
    }
}
