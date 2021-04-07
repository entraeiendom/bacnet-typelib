package no.entra.bacnet.objects;

import no.entra.bacnet.properties.PropertyIdentifier;

import java.util.HashMap;
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

    public ObjectId getObjectId() {
        return objectId;
    }

    public Set<PropertyIdentifier> getPropertyIdentifiers() {
        return propertyIdentifiers;
    }

    public Map<PropertyIdentifier, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<PropertyIdentifier, Object> properties) {
        this.properties = properties;
    }

    public void addProperty(PropertyIdentifier propertyIdentifier, Object value) {
        if (properties == null) {
            properties = new HashMap<>();
        }
        properties.put(propertyIdentifier, value);
    }
}
