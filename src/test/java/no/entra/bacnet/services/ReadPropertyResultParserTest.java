package no.entra.bacnet.services;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        ObjectId expectedObjectId = new ObjectId(ObjectType.AnalogValue, 0);
        Map<String, Object> readResult = readPropertyResult.getReadResult();
        Object objectId = readResult.get("objectId");
        assertEquals(expectedObjectId, objectId);
    }

    @Test
    void parseObjectProperty() throws BacnetParserException {
        String hexString = "294c39024ec4008000004f";
        ReadPropertyResult readPropertyResult = ReadPropertyResultParser.parse(hexString);
        assertNotNull(readPropertyResult);
        assertEquals(2, readPropertyResult.getArrayIndexNumber());
    }
}