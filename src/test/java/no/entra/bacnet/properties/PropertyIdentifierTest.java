package no.entra.bacnet.properties;

import no.entra.bacnet.internal.octet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyIdentifierTest {

    @Test
    void fromOctet() {
        Octet propertyIdentifierOctet = Octet.fromHexString("55");
        PropertyIdentifier propertyIdentifier = PropertyIdentifier.fromOctet(propertyIdentifierOctet);
        assertEquals(PropertyIdentifier.PresentValue, propertyIdentifier);
    }

    @Test
    void getPropertyIdentifierHex() {
        assertEquals("55", PropertyIdentifier.PresentValue.getPropertyIdentifierHex());
    }
}