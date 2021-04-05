package no.entra.bacnet.properties;

import no.entra.bacnet.objects.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static no.entra.bacnet.properties.PropertyIdentifier.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadAccessSpecificationTest {

    @Test
    void buildHexString() {
        List<PropertyIdentifier> propertyIdentifiers = new ArrayList<>();
        propertyIdentifiers.add(ObjectName);
        propertyIdentifiers.add(Description);
        propertyIdentifiers.add(Units);
        propertyIdentifiers.add(PropertyIdentifier.PresentValue);
        ObjectId objectId = new ObjectId(no.entra.bacnet.objects.ObjectType.Device, "8");
        ReadAccessSpecification readAccessSpecification = new ReadAccessSpecification(objectId, propertyIdentifiers);
        String expectedHexString = "0c020000081e094d091c097509551f";
        assertEquals(expectedHexString, readAccessSpecification.buildHexString());
    }

    @Test
    void buildHexStringDynamic() {
        ObjectId objectId = new ObjectId(no.entra.bacnet.objects.ObjectType.Device, "8");
        ReadAccessSpecification readAccessSpecification = new ReadAccessSpecification(objectId, ObjectName, Description, Units, PresentValue );
        String expectedHexString = "0c020000081e094d091c097509551f";
        assertEquals(expectedHexString, readAccessSpecification.buildHexString());
    }
}