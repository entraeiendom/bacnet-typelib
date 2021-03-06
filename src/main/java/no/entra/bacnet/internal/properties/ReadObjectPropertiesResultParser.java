package no.entra.bacnet.internal.properties;

import no.entra.bacnet.internal.apdu.SDContextTag;
import no.entra.bacnet.internal.objects.ObjectIdMapper;
import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.services.BacnetParserException;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static no.entra.bacnet.internal.apdu.ArrayTag.ARRAY1_END;
import static no.entra.bacnet.internal.apdu.ArrayTag.ARRAY1_START;
import static no.entra.bacnet.utils.StringUtils.hasValue;
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
        List<ReadObjectPropertiesResult> readObjectPropertiesResultsList = new ArrayList<>();
        parserResult.setInitialHexString(hexString);
        if (!hasValue(hexString))  {
            throw new BacnetParserException("HexString must contain some data.", parserResult);
        }
        if (hexString.startsWith("81")) {
            throw new BacnetParserException("Please remove BVLC, NPDU and APDU hexString before calling this method. Maybe try BacnetMessageParser.parse(hexString)?", parserResult);
        }

        ObjectId objectId = null;
//        List<ReadPropertyResult> readPropertyResults =  new ArrayList<>();
        OctetReader objectPropertiesReader = new OctetReader(hexString);
//        if (!objectPropertiesReader.hasNext() || !objectPropertiesReader.next().equals(SDContextTag.TAG0LENGTH4)) {
//            parserResult.setUnparsedHexString(objectPropertiesReader.unprocessedHexString());
//            parserResult.setErrorMessage("PropertyResult must start with SD-ContextTag 0 (0c).");
//            parserResult.setParsedOk(false);
//            throw new BacnetParserException("PropertyResult must start with SD-ContextTag 0. (0c)", parserResult);
//        }
        while (objectPropertiesReader.hasNext() && objectPropertiesReader.next().equals(SDContextTag.TAG0LENGTH4)) {
            String objectIdHexString = objectPropertiesReader.next(4);
            ParserResult<ObjectId> objectIdResult = ObjectIdMapper.parse(objectIdHexString);
            if (objectIdResult.isParsedOk()) {
                objectId = objectIdResult.getParsedObject();
                readObjectPropertiesResult = new ReadObjectPropertiesResult(objectId);
//            parserResult.setParsedObject(readObjectPropertiesResult);
                readObjectPropertiesResultsList.add(readObjectPropertiesResult);

                int numberOfOctetsRead = 5;
                if (objectPropertiesReader.next().equals(ARRAY1_START)) {
                    while (objectPropertiesReader.hasNext()) {
                        try {
                            String unprocessedHexString = objectPropertiesReader.unprocessedHexString();
                            if (unprocessedHexString.startsWith(ARRAY1_END.toString())) {
                                objectPropertiesReader.next();

                                break;
                                /*
                                if (unprocessedHexString.length() == 2) {
                                    break;
                                } else {
                                    //Expect start of another array;
                                    objectPropertiesReader.next();
                                    Octet array1Start = objectPropertiesReader.next();
                                    if (!array1Start.equals(ARRAY1_START)) {
                                        throw new BacnetParserException("Expected " + array1Start + " to be equal to " + ARRAY1_START + ". Do not know how to proceed with this unprocessedHexString: " + unprocessedHexString, parserResult);
                                    }
                                    objectIdHexString = objectPropertiesReader.next(4);
                                    objectIdResult = ObjectIdMapper.parse(objectIdHexString);
                                    objectId = objectIdResult.getParsedObject();
                                    readObjectPropertiesResult = new ReadObjectPropertiesResult(objectId);
                                }

                                 */
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
        }
        if (readObjectPropertiesResultsList.size() == 1) {
            parserResult.setParsedObject(readObjectPropertiesResultsList.get(0));
        }
        parserResult.setListOfObjects(readObjectPropertiesResultsList);
        return parserResult;
    }
}
