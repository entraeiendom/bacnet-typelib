package no.entra.bacnet.services;

import no.entra.bacnet.apdu.MeasurementUnit;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import no.entra.bacnet.parseandmap.ParserResult;
import no.entra.bacnet.properties.PropertyIdentifier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Single object multiple properties
{
  "objectId": "analog-value, 0",
  "results": [
    {
      "property-identifier": "object-name",
      "read-result": {
        "Object Name": "UI1_ZoneTemperature"
      }
    },
    {
      "property-identifier": "description",
      "read-result": {
        "description": "Analog Value 0"
      }
    },
    {
      "property-identifier": "units",
      "read-result": {
        "units": "No Units"
      }
    },
    {
      "property-identifier": "present-value",
      "read-result": {
        "Present Value": 22.3999862670898
      }
    }
  ]
}
HexString: 0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f

With Error
{
  "objectId": "device, 8",
  "results": [
    {
      "property-identifier": "object-name",
      "read-result": {
        "Object Name": "FWFCU"
      }
    },
    {
      "property-identifier": "description",
      "read-result": {
        "description": "FW Series Bacnet Device"
      }
    },
    {
      "property-identifier": "present-value",
      "read-result": {
        "propertyAccessError": {
          "errorClass": "property",
          "errorCode": "unknown-property"
        }
      }
    }
  ]
}
HexString: 0c020000081e294d4e75060046574643554f291c4e751800465720536572696573204261636e6574204465766963654f29755e910291205f29555e910291205f1f

With Array
{
  "objectId": "device, 8",
  "results": [
    {
      "property-identifier": "object-list",
      "property-array-index": "1",
      "read-result": {
        "objectId": "device, 8"
      }
    },
    {
      "property-identifier": "object-list",
      "property-array-index": "2",
      "read-result": {
        "objectId": "analog-value, 0"
      }
    }
  ]
}
HexString: 30460e0c020000081e294c39014ec4020000084f294c39024ec4008000004f294c39034e1f
 */
class ReadObjectPropertiesResultParserTest {

    @Test
    void parse() throws BacnetParserException {
        String hexString = "0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f";
        ParserResult<ReadObjectPropertiesResult> parserResult = ReadObjectPropertiesResultParser.parse(hexString);
        assertNotNull(parserResult);
        assertTrue(parserResult.isParsedOk());
        ReadObjectPropertiesResult propertiesResult = parserResult.getParsedObject();
        assertNotNull(propertiesResult);
        ObjectId objectId = new ObjectId(ObjectType.AnalogValue, 0);
        assertEquals(objectId, propertiesResult.getObjectId());
        List<ReadPropertyResult> resultList = propertiesResult.getResults();
        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals("UI1_ZoneTemperature", resultList.get(0).getReadResult().get(PropertyIdentifier.ObjectName));
        assertEquals("Analog Value 0", resultList.get(1).getReadResult().get(PropertyIdentifier.Description));
        assertEquals(MeasurementUnit.NoUnits, resultList.get(2).getReadResult().get(PropertyIdentifier.Units));
        assertEquals(Float.parseFloat("22.3999862670898"), resultList.get(3).getReadResult().get(PropertyIdentifier.PresentValue));
    }

    @Test
    void unparsableHexString() {
        String hexString = "0000";
        assertThrows(BacnetParserException.class, () -> {
            ReadObjectPropertiesResultParser.parse(hexString);
        });
    }
}