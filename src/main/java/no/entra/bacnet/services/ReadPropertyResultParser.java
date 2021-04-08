package no.entra.bacnet.services;

import no.entra.bacnet.apdu.ApplicationTag;
import no.entra.bacnet.apdu.SDContextTag;
import no.entra.bacnet.apdu.ValueType;
import no.entra.bacnet.error.ErrorClassType;
import no.entra.bacnet.error.ErrorCodeType;
import no.entra.bacnet.mappers.MapperResult;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectIdMapper;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.octet.OctetReader;
import no.entra.bacnet.properties.PropertyIdentifier;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static no.entra.bacnet.apdu.SDContextTag.*;
import static no.entra.bacnet.mappers.StringMapper.parseCharStringExtended;
import static no.entra.bacnet.utils.HexUtils.toInt;
import static org.slf4j.LoggerFactory.getLogger;

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
    private static final Logger log = getLogger(ReadPropertyResultParser.class);

    public static final char ExtendedValue = '5';
    public static final String ERROR_CODE = "ErrorCode";
    public static final String ERROR_CLASS = "ErrorClass";

    public static ReadPropertyResult parse(String hexString) throws BacnetParserException {
        OctetReader propertyReader = new OctetReader(hexString);
        //Expect PropertyReader to start with 29 - SD-ContextTag2-Length1
        Octet sdContextTag2 = propertyReader.next();
        if (!sdContextTag2.equals(SDContextTag.TAG2LENGTH1)) {
            BacnetParserResult bacnetParserResult = new BacnetParserResult(hexString, propertyReader.unprocessedHexString(), "PropertyResult must start with SD-ContextTag 2. Value is: " + sdContextTag2);
            throw new BacnetParserException("PropertyResult must start with SD-ContextTag 2. Value is: " + sdContextTag2, bacnetParserResult);
        }
        ReadPropertyResult readPropertyResult = null;
        final Octet propertyIdentifierOctet = propertyReader.next();
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
            Octet applicationTagOctet = propertyReader.next();
            ApplicationTag applicationTag = new ApplicationTag(applicationTagOctet);
            Object value = null;

            ValueType valueType = applicationTag.findValueType();
            switch (valueType) {
                case Long:
                case Integer:
                    int length = applicationTag.findLength();
                    value = toInt(propertyReader.nextOctets(length));
                    readPropertyResult.addReadResult(propertyIdentifier, value);
                    break;
                case ObjectIdentifier:
                    final String objectIdHexString = propertyReader.next(4);
                    MapperResult<ObjectId> idResult = ObjectIdMapper.parse(objectIdHexString);
                    ObjectId propertyObjectId = idResult.getParsedObject();
                    readPropertyResult.addReadResult(propertyIdentifier, propertyObjectId);
                    break;
                case CharString:
                    if (applicationTagOctet.getSecondNibble() == '5') {
                        MapperResult<String> stringResult = parseCharStringExtended(propertyReader.unprocessedHexString());
                        String text = stringResult.getParsedObject();
                        readPropertyResult.addReadResult(propertyIdentifier, text);
                        propertyReader.next(stringResult.getNumberOfOctetsRead());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Not implemented yet, " + valueType);
            }
            if (propertyReader.unprocessedHexString().startsWith(TAG4END.toString())) {
                propertyReader.next();
            }
            readPropertyResult.setUnparsedHexString(propertyReader.unprocessedHexString());
        } else if (readResultOctet.equals(TAG5START)) {
            //property-access-error
            Octet applicationTagOctet = propertyReader.next();
            ApplicationTag applicationTag = new ApplicationTag(applicationTagOctet);
            if (applicationTag.findValueType().equals(ValueType.Enumerated)) {
                Octet errorClassOctet = propertyReader.next();
                ErrorClassType errorClass = ErrorClassType.fromChar(errorClassOctet.getSecondNibble());
                Map<String, Enum> errorMap = new HashMap<>();
                errorMap.put(ERROR_CLASS, errorClass);
                readPropertyResult.addReadResult(PropertyIdentifier.XxError, errorMap);
                applicationTagOctet = propertyReader.next();
                applicationTag = new ApplicationTag(applicationTagOctet);
                if (applicationTag.findValueType().equals(ValueType.Enumerated)) {
                    Octet errorCodeOctet = propertyReader.next();
                    ErrorCodeType errorCode = ErrorCodeType.fromOctet(errorCodeOctet);
                    errorMap.put(ERROR_CODE, errorCode);
                }
                if (propertyReader.unprocessedHexString().startsWith(TAG5END.toString())) {
                    propertyReader.next();
                }
                readPropertyResult.setUnparsedHexString(propertyReader.unprocessedHexString());
            }
        } else {
            readPropertyResult.setInitialHexString(hexString);
            readPropertyResult.setUnparsedHexString(propertyReader.unprocessedHexString());
            String errorMessage = "Could not determine how to parse \"read-result\" starting with octet " + readResultOctet;
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
