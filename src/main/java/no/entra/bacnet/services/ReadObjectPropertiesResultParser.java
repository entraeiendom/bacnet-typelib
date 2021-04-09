package no.entra.bacnet.services;

import no.entra.bacnet.apdu.SDContextTag;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectIdMapper;
import no.entra.bacnet.octet.OctetReader;
import no.entra.bacnet.parseandmap.ParserResult;
import org.slf4j.Logger;

import static no.entra.bacnet.apdu.ArrayTag.ARRAY1_END;
import static no.entra.bacnet.apdu.ArrayTag.ARRAY1_START;
import static org.slf4j.LoggerFactory.getLogger;

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
public class ReadObjectPropertiesResultParser {
    private static final Logger log = getLogger(ReadObjectPropertiesResultParser.class);

    public static ParserResult<ReadObjectPropertiesResult> parse(String hexString) throws BacnetParserException {
        ParserResult<ReadObjectPropertiesResult> parserResult = new ParserResult<>();
        ReadObjectPropertiesResult readObjectPropertiesResult = null;
        parserResult.setInitialHexString(hexString);
        ObjectId objectId = null;
//        List<ReadPropertyResult> readPropertyResults =  new ArrayList<>();
        OctetReader objectPropertiesReader = new OctetReader(hexString);
        if (!objectPropertiesReader.hasNext() || !objectPropertiesReader.next().equals(SDContextTag.TAG0LENGTH4)) {
            parserResult.setUnparsedHexString(objectPropertiesReader.unprocessedHexString());
            parserResult.setErrorMessage("PropertyResult must start with SD-ContextTag 0 (0c).");
            parserResult.setParsedOk(false);
            throw new BacnetParserException("PropertyResult must start with SD-ContextTag 0. (0c)", parserResult);
        }
        String objectIdHexString = objectPropertiesReader.next(4);
        ParserResult<ObjectId> objectIdResult = ObjectIdMapper.parse(objectIdHexString);
        if (objectIdResult.isParsedOk()) {
            objectId = objectIdResult.getParsedObject();
            readObjectPropertiesResult = new ReadObjectPropertiesResult(objectId);
            parserResult.setParsedObject(readObjectPropertiesResult);

            int numberOfOctetsRead = 5;
            if (objectPropertiesReader.next().equals(ARRAY1_START)) {
                while (objectPropertiesReader.hasNext()) {
                    try {
                        String unprocessedHexString = objectPropertiesReader.unprocessedHexString();
                        if (unprocessedHexString.startsWith(ARRAY1_END.toString())) {
                            break;
                        }
                        ParserResult<ReadPropertyResult> propertyParserResult = ReadPropertyResultParser.parse(unprocessedHexString);
                        if (parserResult.isParsedOk()) {
                            readObjectPropertiesResult.addReadPropertyResult(propertyParserResult.getParsedObject());
                            numberOfOctetsRead = propertyParserResult.getNumberOfOctetsRead();
                            objectPropertiesReader.next(numberOfOctetsRead);
                        }
                    } catch (IllegalStateException e) {
                        parserResult.setUnparsedHexString(objectPropertiesReader.unprocessedHexString());
                        parserResult.setErrorMessage(e.getMessage());
                        parserResult.setParsedOk(false);
                        break;
                    } catch (BacnetParserException e) {
                        log.trace("Could not parse ReadObjectPropertiesResult from hexString: {}. Unparsed: {} ", hexString, e.getParserResult().getUnparsedHexString());
                        break;
                    }
                }
            } else {
                parserResult.setParsedOk(false);
                parserResult.setErrorMessage("Could not parse ObjectId");
                parserResult.setUnparsedHexString(objectIdResult.getUnparsedHexString());
            }
        } else {
            parserResult.setParsedOk(false);
            parserResult.setErrorMessage("Missing array/sequence of ReadPropertyResult");
            parserResult.setUnparsedHexString(objectIdResult.getUnparsedHexString());
        }
        return parserResult;
    }
}
