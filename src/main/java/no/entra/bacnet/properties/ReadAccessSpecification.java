package no.entra.bacnet.properties;

import no.entra.bacnet.apdu.SDContextTag;
import no.entra.bacnet.objects.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static no.entra.bacnet.apdu.ArrayTag.ARRAY1_END;
import static no.entra.bacnet.apdu.ArrayTag.ARRAY1_START;

public class ReadAccessSpecification {
    private final ObjectId objectIdentifier;
    private final List<PropertyIdentifier> propertyIdentifiers;


    public ReadAccessSpecification(ObjectId objectIdentifier, List<PropertyIdentifier> propertyIdentifiers) {
        this.objectIdentifier = objectIdentifier;
        this.propertyIdentifiers = propertyIdentifiers;
    }

    public ReadAccessSpecification(ObjectId objectIdentifier, PropertyIdentifier... propertyIdentifier) {
        this.objectIdentifier = objectIdentifier;
        propertyIdentifiers = new ArrayList<>();
        for (PropertyIdentifier identifier : propertyIdentifier) {
            propertyIdentifiers.add(identifier);
        }
    }

    public String buildHexString() {
        String hexString = "";
        String propertyReferencesHexString = ARRAY1_START.toString();
        for (PropertyIdentifier propertyIdentifier : propertyIdentifiers) {
            propertyReferencesHexString += SDContextTag.TAG0LENGTH1 + propertyIdentifier.getPropertyIdentifierHex();
        }
        propertyReferencesHexString += ARRAY1_END;
            hexString += SDContextTag.TAG0LENGTH4 + objectIdentifier.toHexString() + propertyReferencesHexString;
        return hexString;
    }

    public ObjectId getObjectIdentifier() {
        return objectIdentifier;
    }

    public List<PropertyIdentifier> getPropertyIdentifiers() {
        return propertyIdentifiers;
    }
}
