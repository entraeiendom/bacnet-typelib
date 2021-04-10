package no.entra.bacnet.internal.properties;

import no.entra.bacnet.error.ErrorClassType;
import no.entra.bacnet.error.ErrorCodeType;
import no.entra.bacnet.internal.apdu.MeasurementUnit;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.parseandmap.ParserResult;
import no.entra.bacnet.services.BacnetParserException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static no.entra.bacnet.internal.properties.ReadPropertyResultParser.ERROR_CLASS;
import static no.entra.bacnet.internal.properties.ReadPropertyResultParser.ERROR_CODE;
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

Enumerated - units
  {
    "property-identifier": "units",
    "read-result": {
      "units": "No Units"
    }
  }
  HexString: 29754e915f4f
Present Value
 {
   "property-identifier": "present-value",
   "read-result": {
     "present-value": 22.3999862670898
   }
 }
  HexString: 29554e4441b3332c4f
 */
class ReadPropertyResultParserTest {

    @Test
    void parseObjectList() throws BacnetParserException {
        String hexString = "294c39024ec4008000004f";
        ParserResult<ReadPropertyResult> parserResult = ReadPropertyResultParser.parse(hexString);
        assertNotNull(parserResult);
        ReadPropertyResult readPropertyResult = parserResult.getParsedObject();
        assertEquals(2, readPropertyResult.getArrayIndexNumber());
        ObjectId expectedObjectId = new ObjectId(AnalogValue, 0);
        Map<PropertyIdentifier, Object> readResult = readPropertyResult.getReadResult();
        ObjectId objectId = (ObjectId) readResult.get(PropertyIdentifier.ObjectList);
        assertEquals(expectedObjectId, objectId);
        assertEquals(0, objectId.getInstanceNumber());
        assertEquals(AnalogValue, objectId.getObjectType());
        assertTrue(parserResult.isParsedOk());
    }

    @Test
    void parseObjectProperty() throws BacnetParserException {
        String hexString = "291c4e751800465720536572696573204261636e6574204465766963654f";
        ParserResult<ReadPropertyResult> parserResult = ReadPropertyResultParser.parse(hexString);
        assertNotNull(parserResult);
        assertTrue(parserResult.isParsedOk());
        ReadPropertyResult readPropertyResult = parserResult.getParsedObject();
        assertNotNull(readPropertyResult);
        assertEquals(null, readPropertyResult.getArrayIndexNumber());
        assertEquals(PropertyIdentifier.Description, readPropertyResult.getPropertyIdentifier());
        assertEquals("FW Series Bacnet Device", readPropertyResult.getReadResult().get(PropertyIdentifier.Description));
    }

    @Test
    void parseErrorObject() throws BacnetParserException {
        String hexString = "29555e910291205f";
        ParserResult<ReadPropertyResult> parserResult = ReadPropertyResultParser.parse(hexString);
        assertNotNull(parserResult);
        assertTrue(parserResult.isParsedOk());
        ReadPropertyResult readPropertyResult = parserResult.getParsedObject();
        Map<String, Enum> errorMap = (Map<String, Enum>) readPropertyResult.getReadResult().get(PropertyIdentifier.XxError);
        assertNotNull(errorMap);
        assertEquals(ErrorClassType.property, errorMap.get(ERROR_CLASS));
        assertEquals(ErrorCodeType.UnknownProperty, errorMap.get(ERROR_CODE));
    }

    @Test
    void units() throws BacnetParserException {
        String hexString = "29754e915f4f";
        ParserResult<ReadPropertyResult> parserResult = ReadPropertyResultParser.parse(hexString);
        assertNotNull(parserResult);
        assertTrue(parserResult.isParsedOk());
        ReadPropertyResult readPropertyResult = parserResult.getParsedObject();
        assertEquals(PropertyIdentifier.Units, readPropertyResult.getPropertyIdentifier());
        assertEquals(MeasurementUnit.NoUnits, readPropertyResult.getReadResult().get(PropertyIdentifier.Units));
    }

    @Test
    void presentValue() throws BacnetParserException {
        String hexString = "29554e4441b3332c4f";
        ParserResult<ReadPropertyResult> parserResult = ReadPropertyResultParser.parse(hexString);
        assertNotNull(parserResult);
        assertTrue(parserResult.isParsedOk());
        ReadPropertyResult readPropertyResult = parserResult.getParsedObject();
        assertEquals(PropertyIdentifier.PresentValue, readPropertyResult.getPropertyIdentifier());
        assertEquals(Float.parseFloat("22.3999862670898"), readPropertyResult.getReadResult().get(PropertyIdentifier.PresentValue));
    }
}