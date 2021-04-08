package no.entra.bacnet.services;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.properties.PropertyIdentifier;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static no.entra.bacnet.objects.ObjectType.AnalogValue;
import static org.junit.jupiter.api.Assertions.*;

/*
Structure
Array
  {
    "property-identifier": "object-list",
    "property-array-index": "2",
    "read-result": {
      "objectId": "analog-value, 0"
    }
  }
  HexString: 294c39024ec4008000004f
Property
  {
    "property-identifier": "description",
    "read-result": {
      "description": "FW Series Bacnet Device"
    }
  }
  HexString: 291c4e751800465720536572696573204261636e6574204465766963654f
Error
  {
    "property-identifier": "present-value",
    "read-result": {
      "propertyAccessError": {
        "errorClass": "property",
        "errorCode": "unknown-property"
      }
    }
  }
  HexString: 29555e910291205f
 */
class ReadPropertyResultParserTest {

    @Test
    void parseObjectList() throws BacnetParserException {
        String hexString = "294c39024ec4008000004f";
        ReadPropertyResult readPropertyResult = ReadPropertyResultParser.parse(hexString);
        assertNotNull(readPropertyResult);
        assertEquals(2, readPropertyResult.getArrayIndexNumber());
        ObjectId expectedObjectId = new ObjectId(AnalogValue, 0);
        Map<PropertyIdentifier, Object> readResult = readPropertyResult.getReadResult();
        ObjectId objectId = (ObjectId) readResult.get(PropertyIdentifier.ObjectList);
        assertEquals(expectedObjectId, objectId);
        assertEquals(0, objectId.getInstanceNumber());
        assertEquals(AnalogValue, objectId.getObjectType());
        assertTrue(readPropertyResult.isParsedOk());
    }

    @Test
    void parseObjectProperty() throws BacnetParserException {
        String hexString = "291c4e751800465720536572696573204261636e6574204465766963654f";
        ReadPropertyResult readPropertyResult = ReadPropertyResultParser.parse(hexString);
        assertNotNull(readPropertyResult);
        assertTrue(readPropertyResult.isParsedOk());
        assertEquals(null, readPropertyResult.getArrayIndexNumber());
        assertEquals(PropertyIdentifier.Description, readPropertyResult.getPropertyIdentifier());
        assertEquals("FW Series Bacnet Device", readPropertyResult.getReadResult().get(PropertyIdentifier.Description));
    }
}