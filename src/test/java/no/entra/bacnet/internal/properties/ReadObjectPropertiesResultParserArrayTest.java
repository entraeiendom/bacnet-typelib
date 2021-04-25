package no.entra.bacnet.internal.properties;

import no.entra.bacnet.BacnetMessageParser;
import no.entra.bacnet.BacnetResponse;
import no.entra.bacnet.device.DeviceId;
import no.entra.bacnet.error.ErrorClassType;
import no.entra.bacnet.error.ErrorCodeType;
import no.entra.bacnet.internal.apdu.MeasurementUnit;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import no.entra.bacnet.properties.ReadPropertyMultipleResponse;
import no.entra.bacnet.properties.ReadPropertyMultipleService;
import no.entra.bacnet.services.BacnetParserException;
import no.entra.bacnet.services.Service;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReadObjectPropertiesResultParserArrayTest {

    /*
    Example result, not the same as in the test below. Structure is the same. Names are different.
    One Device, multiple objects with multiple properties
[
  {
    "objectId": "Device, 8",
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
  },
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
  },
  {
    "objectId": "analog-value, 1",
    "results": [
      {
        "property-identifier": "object-name",
        "read-result": {
          "Object Name": "UI2_ZoneTemperature"
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
]
     */
    @Test
    void objectsPropertiesArrayTest() throws BacnetParserException {
        String hexString = "0c020000081e294d4e75060046574643554f291c4e751800465720536572696573204261636e6574204465766963654f29755e910291205f29555e910291205f1f0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f0c008000011e294d4e7519005549325f44697363686172676554656d70657261747572654f291c4e750f00416e616c6f672056616c756520314f29754e915f4f29554e4441acccf84f1f0c008000021e294d4e751300554f315f537570706c7946616e53706565644f291c4e750f00416e616c6f672056616c756520324f29754e915f4f29554e44000000004f1f0c008000031e294d4e751100554f325f436f6f6c696e6756616c76654f291c4e750f00416e616c6f672056616c756520334f29754e915f4f29554e44000000004f1f0c008000041e294d4e751100554f335f48656174696e6756616c76654f291c4e750f00416e616c6f672056616c756520344f29754e915f4f29554e4442b90d164f1f0c008000051e294d4e7512004f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520354f29754e915f4f29554e4441f000004f1f0c008000061e294d4e751400556e6f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520364f29754e915f4f29554e44415000004f1f0c008000071e294d4e751900456666656374697665436f6f6c696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520374f29754e915f4f29554e4441f800004f1f0c008000081e294d4e75190045666665637469766548656174696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520384f29754e915f4f29554e4441b000004f1f0c008000091e294d4e750f00416e616c6f672056616c756520394f291c4e750f00416e616c6f672056616c756520394f29754e915f4f29554e44000000004f1f0c0080000a1e294d4e751000416e616c6f672056616c75652031304f291c4e751000416e616c6f672056616c75652031304f29754e915f4f29554e44000000004f1f0c0080000b1e294d4e751000416e616c6f672056616c75652031314f291c4e751000416e616c6f672056616c75652031314f29754e915f4f29554e44000000004f1f0c0080000c1e294d4e751000416e616c6f672056616c75652031324f291c4e751000416e616c6f672056616c75652031324f29754e915f4f29554e44000000004f1f0c0080000d1e294d4e751000416e616c6f672056616c75652031334f291c4e751000416e616c6f672056616c75652031334f29754e915f4f29554e44000000004f1f0c0080000e1e294d4e751000416e616c6f672056616c75652031344f291c4e751000416e616c6f672056616c75652031344f29754e915f4f29554e44000000004f1f0c0080000f1e294d4e751000416e616c6f672056616c75652031354f291c4e751000416e616c6f672056616c75652031354f29754e915f4f29554e44000000004f1f0c008000101e294d4e751000416e616c6f672056616c75652031364f291c4e751000416e616c6f672056616c75652031364f29754e915f4f29554e44000000004f1f0c008000111e294d4e751000416e616c6f672056616c75652031374f291c4e751000416e616c6f672056616c75652031374f29754e915f4f29554e44000000004f1f0c008000121e294d4e751000416e616c6f672056616c75652031384f291c4e751000416e616c6f672056616c75652031384f29754e915f4f29554e44000000004f1f";
        ParserResult<ReadObjectPropertiesResult> parserResult = ReadObjectPropertiesResultParser.parse(hexString);
        assertNotNull(parserResult);

        //Expecting list of 20 objectPropertiesResult
        List<ReadObjectPropertiesResult> objectPropertiesResults = parserResult.getListOfObjects();
        assertEquals(20, objectPropertiesResults.size());
        //First element is Device 8
        ReadObjectPropertiesResult device8Result = objectPropertiesResults.get(0);
        assertEquals(new DeviceId(8), device8Result.getObjectId());
        assertEquals(PropertyIdentifier.ObjectName, device8Result.getResults().get(0).getPropertyIdentifier());
        assertEquals("FWFCU", device8Result.getResults().get(0).getReadResult().get(PropertyIdentifier.ObjectName));

        assertEquals(PropertyIdentifier.Description, device8Result.getResults().get(1).getPropertyIdentifier());
        assertEquals(PropertyIdentifier.Units, device8Result.getResults().get(2).getPropertyIdentifier());
        assertEquals(PropertyIdentifier.PresentValue, device8Result.getResults().get(3).getPropertyIdentifier());
        assertNull(device8Result.getResults().get(3).getReadResult().get(PropertyIdentifier.PresentValue));
        HashMap errorMap = (HashMap) device8Result.getResults().get(3).getReadResult().get(PropertyIdentifier.XxError);
        assertEquals(ErrorClassType.property, errorMap.get("ErrorClass"));
        assertEquals(ErrorCodeType.UnknownProperty, errorMap.get("ErrorCode"));

        //Second element is AnalogValue 0
        ReadObjectPropertiesResult analogValue0 = objectPropertiesResults.get(1);
        assertEquals(new ObjectId(ObjectType.AnalogValue,0), analogValue0.getObjectId());
        assertEquals(PropertyIdentifier.ObjectName, analogValue0.getResults().get(0).getPropertyIdentifier());
        assertEquals("UI1_ZoneTemperature", analogValue0.getResults().get(0).getReadResult().get(PropertyIdentifier.ObjectName));
        assertEquals(PropertyIdentifier.Description, analogValue0.getResults().get(1).getPropertyIdentifier());
        assertEquals("Analog Value 0", analogValue0.getResults().get(1).getReadResult().get(PropertyIdentifier.Description));
        assertEquals(PropertyIdentifier.Units, analogValue0.getResults().get(2).getPropertyIdentifier());
        assertEquals(MeasurementUnit.NoUnits, analogValue0.getResults().get(2).getReadResult().get(PropertyIdentifier.Units));
        assertEquals(PropertyIdentifier.PresentValue, analogValue0.getResults().get(3).getPropertyIdentifier());
        assertEquals(Float.valueOf("22.399986"), analogValue0.getResults().get(3).getReadResult().get(PropertyIdentifier.PresentValue));

        //Other verifications
        assertEquals(1350, parserResult.getNumberOfOctetsRead());
        assertEquals("", parserResult.getUnparsedHexString());
        ReadObjectPropertiesResult objectPropertiesResult = parserResult.getParsedObject();
        assertNull(objectPropertiesResult);
    }

    @Test
    void objectsPropertiesArrayBacnetMessageParser() {
        String hexString = "810a054f010030470e0c020000081e294d4e75060046574643554f291c4e751800465720536572696573204261636e6574204465766963654f29755e910291205f29555e910291205f1f0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f0c008000011e294d4e7519005549325f44697363686172676554656d70657261747572654f291c4e750f00416e616c6f672056616c756520314f29754e915f4f29554e4441acccf84f1f0c008000021e294d4e751300554f315f537570706c7946616e53706565644f291c4e750f00416e616c6f672056616c756520324f29754e915f4f29554e44000000004f1f0c008000031e294d4e751100554f325f436f6f6c696e6756616c76654f291c4e750f00416e616c6f672056616c756520334f29754e915f4f29554e44000000004f1f0c008000041e294d4e751100554f335f48656174696e6756616c76654f291c4e750f00416e616c6f672056616c756520344f29754e915f4f29554e4442b90d164f1f0c008000051e294d4e7512004f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520354f29754e915f4f29554e4441f000004f1f0c008000061e294d4e751400556e6f63637570616e6379536574706f696e744f291c4e750f00416e616c6f672056616c756520364f29754e915f4f29554e44415000004f1f0c008000071e294d4e751900456666656374697665436f6f6c696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520374f29754e915f4f29554e4441f800004f1f0c008000081e294d4e75190045666665637469766548656174696e67536574706f696e744f291c4e750f00416e616c6f672056616c756520384f29754e915f4f29554e4441b000004f1f0c008000091e294d4e750f00416e616c6f672056616c756520394f291c4e750f00416e616c6f672056616c756520394f29754e915f4f29554e44000000004f1f0c0080000a1e294d4e751000416e616c6f672056616c75652031304f291c4e751000416e616c6f672056616c75652031304f29754e915f4f29554e44000000004f1f0c0080000b1e294d4e751000416e616c6f672056616c75652031314f291c4e751000416e616c6f672056616c75652031314f29754e915f4f29554e44000000004f1f0c0080000c1e294d4e751000416e616c6f672056616c75652031324f291c4e751000416e616c6f672056616c75652031324f29754e915f4f29554e44000000004f1f0c0080000d1e294d4e751000416e616c6f672056616c75652031334f291c4e751000416e616c6f672056616c75652031334f29754e915f4f29554e44000000004f1f0c0080000e1e294d4e751000416e616c6f672056616c75652031344f291c4e751000416e616c6f672056616c75652031344f29754e915f4f29554e44000000004f1f0c0080000f1e294d4e751000416e616c6f672056616c75652031354f291c4e751000416e616c6f672056616c75652031354f29754e915f4f29554e44000000004f1f0c008000101e294d4e751000416e616c6f672056616c75652031364f291c4e751000416e616c6f672056616c75652031364f29754e915f4f29554e44000000004f1f0c008000111e294d4e751000416e616c6f672056616c75652031374f291c4e751000416e616c6f672056616c75652031374f29754e915f4f29554e44000000004f1f0c008000121e294d4e751000416e616c6f672056616c75652031384f291c4e751000416e616c6f672056616c75652031384f29754e915f4f29554e44000000004f1f";
        BacnetResponse bacnetMessageResponse = BacnetMessageParser.parse(hexString);
        assertEquals(71, bacnetMessageResponse.getInvokeId());
        Service service = bacnetMessageResponse.getService();
        assertTrue(service instanceof ReadPropertyMultipleService);
        ReadPropertyMultipleService propertiesService = (ReadPropertyMultipleService)service;
        ReadPropertyMultipleResponse propertiesResponse = propertiesService.getReadPropertyMultipleResponse();

        //Expecting list of 20 objectPropertiesResult
        List<ReadObjectPropertiesResult> objectPropertiesResults = propertiesResponse.getResults();
        assertEquals(20, objectPropertiesResults.size());
        //First element is Device 8
        ReadObjectPropertiesResult device8Result = objectPropertiesResults.get(0);
        assertEquals(new DeviceId(8), device8Result.getObjectId());
        assertEquals(PropertyIdentifier.ObjectName, device8Result.getResults().get(0).getPropertyIdentifier());
        assertEquals("FWFCU", device8Result.getResults().get(0).getReadResult().get(PropertyIdentifier.ObjectName));

        assertEquals(PropertyIdentifier.Description, device8Result.getResults().get(1).getPropertyIdentifier());
        assertEquals(PropertyIdentifier.Units, device8Result.getResults().get(2).getPropertyIdentifier());
        assertEquals(PropertyIdentifier.PresentValue, device8Result.getResults().get(3).getPropertyIdentifier());
        assertNull(device8Result.getResults().get(3).getReadResult().get(PropertyIdentifier.PresentValue));
        HashMap errorMap = (HashMap) device8Result.getResults().get(3).getReadResult().get(PropertyIdentifier.XxError);
        assertEquals(ErrorClassType.property, errorMap.get("ErrorClass"));
        assertEquals(ErrorCodeType.UnknownProperty, errorMap.get("ErrorCode"));

        //Second element is AnalogValue 0
        ReadObjectPropertiesResult analogValue0 = objectPropertiesResults.get(1);
        assertEquals(new ObjectId(ObjectType.AnalogValue,0), analogValue0.getObjectId());
        assertEquals(PropertyIdentifier.ObjectName, analogValue0.getResults().get(0).getPropertyIdentifier());
        assertEquals("UI1_ZoneTemperature", analogValue0.getResults().get(0).getReadResult().get(PropertyIdentifier.ObjectName));
        assertEquals(PropertyIdentifier.Description, analogValue0.getResults().get(1).getPropertyIdentifier());
        assertEquals("Analog Value 0", analogValue0.getResults().get(1).getReadResult().get(PropertyIdentifier.Description));
        assertEquals(PropertyIdentifier.Units, analogValue0.getResults().get(2).getPropertyIdentifier());
        assertEquals(MeasurementUnit.NoUnits, analogValue0.getResults().get(2).getReadResult().get(PropertyIdentifier.Units));
        assertEquals(PropertyIdentifier.PresentValue, analogValue0.getResults().get(3).getPropertyIdentifier());
        assertEquals(Float.valueOf("22.399986"), analogValue0.getResults().get(3).getReadResult().get(PropertyIdentifier.PresentValue));

    }

    @Test
    void unexpectedHexString() {
        String hexString = "810a0101010101";
        assertThrows(BacnetParserException.class, () -> ReadObjectPropertiesResultParser.parse(hexString));
    }
}
