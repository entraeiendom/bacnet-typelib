package no.entra.bacnet.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyReferenceTest {

    @Test
    void buildHexString() {
        PropertyReference propertyReference = new PropertyReference(PropertyIdentifier.ObjectList, 3);
        assertEquals("094c1903", propertyReference.buildHexString());
        propertyReference = new PropertyReference(PropertyIdentifier.ObjectList, Integer.valueOf("01ff", 16));
        assertEquals("094c1a01ff", propertyReference.buildHexString());
        propertyReference = new PropertyReference(PropertyIdentifier.ObjectName);
        assertEquals("094d", propertyReference.buildHexString());


    }
}