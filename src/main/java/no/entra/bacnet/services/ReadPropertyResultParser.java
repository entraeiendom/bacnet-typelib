package no.entra.bacnet.services;

import no.entra.bacnet.apdu.ApplicationTag;
import no.entra.bacnet.apdu.SDContextTag;
import no.entra.bacnet.apdu.ValueType;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectIdMapper;
import no.entra.bacnet.objects.ObjectIdMapperResult;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.octet.OctetReader;
import no.entra.bacnet.properties.PropertyIdentifier;

import static no.entra.bacnet.apdu.SDContextTag.TAG4START;
import static no.entra.bacnet.apdu.SDContextTag.TAG5START;
import static no.entra.bacnet.utils.HexUtils.toInt;

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
public class ReadPropertyResultParser {

    public static ReadPropertyResult parse(String hexString) throws BacnetParserException {
        OctetReader propertyReader = new OctetReader(hexString);
        //Expect PropertyReader to start with 29 - SD-ContextTag2-Length1
        Octet sdContextTag2 = propertyReader.next();
        if (!sdContextTag2.equals(SDContextTag.TAG2LENGTH1)) {
            BacnetParserResult bacnetParserResult = new BacnetParserResult(hexString, propertyReader.unprocessedHexString(), "PropertyResult must start with SD-ContextTag 2. Value is: " + sdContextTag2);
            throw new BacnetParserException("PropertyResult must start with SD-ContextTag 2. Value is: " + sdContextTag2, bacnetParserResult);
        }
        ReadPropertyResult readPropertyResult = null;
        Octet propertyIdentifierOctet = propertyReader.next();
        PropertyIdentifier propertyIdentifier = PropertyIdentifier.fromOctet(propertyIdentifierOctet);
        readPropertyResult = new ReadPropertyResult(propertyIdentifier);

        //If property identifier is an array, check if array index is included
        if (propertyIdentifier.equals(PropertyIdentifier.ObjectList)) {
            Octet arrayIndexTag = propertyReader.next();

            if (arrayIndexTag.getFirstNibble() == SDContextTag.TAG3LENGTH1.getFirstNibble()) {
                SDContextTag sdContextTag = new SDContextTag(arrayIndexTag);
                int numberofOctets = sdContextTag.findLength();
                String numberHex = propertyReader.next(numberofOctets);
                int arrayIndexNumber = toInt(numberHex);
                readPropertyResult.setArrayIndexNumber(arrayIndexNumber);
            }
        }
        //Expect next octet to be either property-value (4e) or property-access-error (4f)
        Octet readResultOctet = propertyReader.next();
        //property-value
        if (readResultOctet.equals(TAG4START)) {
            ApplicationTag applicationTag = new ApplicationTag(propertyReader.next());
            Object value = null;

            ValueType valueType = applicationTag.findValueType();
            switch (valueType) {
                case Long:
                case Integer:
                    int length = applicationTag.findLength();
                    value = toInt(propertyReader.nextOctets(length));
                    readPropertyResult.addReadResult(propertyIdentifier.name(), value);
                    break;
                case ObjectIdentifier:
                    ObjectIdMapperResult<ObjectId> idResult = ObjectIdMapper.parse(propertyReader.next(5));
                    ObjectId propertyObjectId = idResult.getParsedObject();
                    readPropertyResult.addReadResult("objectId", propertyObjectId);
                    break;
                default:
                    throw new IllegalArgumentException("Not implemented yet, " + valueType);
            }
        } else if (readResultOctet.equals(TAG5START)) {
            //property-access-error
            //FIXME Error handling on ReadPropertyMultiple
            throw new IllegalArgumentException("Not implemented yet");
        } else {
            readPropertyResult.setInitialHexString(hexString);
            readPropertyResult.setUnparsedHexString(propertyReader.unprocessedHexString());
            String errorMessage = "Could not deterimine how to parse \"read-result\" starting with octet " + readResultOctet;
            readPropertyResult.setErrorMessage(errorMessage);
            readPropertyResult.setParsedOk(false);
            throw new BacnetParserException(errorMessage, readPropertyResult);
        }

        readPropertyResult.setInitialHexString(hexString);
        readPropertyResult.setUnparsedHexString(propertyReader.unprocessedHexString());
        readPropertyResult.setParsedOk(true);

        return readPropertyResult;
    }
}
