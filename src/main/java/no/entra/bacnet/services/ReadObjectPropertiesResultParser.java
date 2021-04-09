package no.entra.bacnet.services;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectIdMapper;
import no.entra.bacnet.octet.OctetReader;
import no.entra.bacnet.parseandmap.ParserResult;

import java.util.ArrayList;
import java.util.List;

/*
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
 */
public class ReadObjectPropertiesResultParser {

    public static ReadObjectPropertiesResult parse(String hexString) throws BacnetParserException {
        ObjectId objectId = null;// TODO: 08.04.2021
        List<ReadPropertyResult> readPropertyResults =  new ArrayList<>();//TODO
        //0c008000001e294d4e7514005549315f5a6f6e6554656d70657261747572654f291c4e750f00416e616c6f672056616c756520304f29754e915f4f29554e4441b3332c4f1f
        OctetReader objectPropertiesReader = new OctetReader(hexString);
        ParserResult<ObjectId> objectIdResult = ObjectIdMapper.parse(hexString);
        objectId = objectIdResult.getParsedObject();
        int numberOfOctetsRead = objectIdResult.getNumberOfOctetsRead();
        objectPropertiesReader.next(numberOfOctetsRead); //Discard and move pointer
        while (objectPropertiesReader.hasNext()) {

        }
        //Iterate over list of
        return null;
    }
}
