package no.entra.bacnet.internal.property;

import no.entra.bacnet.internal.apdu.ApplicationTag;
import no.entra.bacnet.internal.apdu.MeasurementUnit;
import no.entra.bacnet.internal.apdu.SDContextTag;
import no.entra.bacnet.internal.apdu.ValueType;
import no.entra.bacnet.internal.objects.ObjectIdMapper;
import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.internal.properties.PropertyIdentifier;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.services.BacnetParserException;
import org.slf4j.Logger;

import static no.entra.bacnet.internal.apdu.SDContextTag.TAG3START;
import static no.entra.bacnet.internal.parseandmap.StringParser.parseCharStringExtended;
import static no.entra.bacnet.utils.HexUtils.*;
import static org.slf4j.LoggerFactory.getLogger;

public class ReadSinglePropertyResultParser {
    private static final Logger log = getLogger(ReadSinglePropertyResultParser.class);

    public static ParserResult<ReadSinglePropertyResult> parse(String hexString) throws BacnetParserException {
        ParserResult<ReadSinglePropertyResult> parserResult = new ParserResult();
        parserResult.setInitialHexString(hexString);
        OctetReader propertyReader = new OctetReader(hexString);

        ReadSinglePropertyResult propertyResult = new ReadSinglePropertyResult();
        //1 Read ObjectId
        Octet sdContextTag0 = propertyReader.next();
        if (!sdContextTag0.equals(SDContextTag.TAG0LENGTH4)) {
            parserResult.setUnparsedHexString(propertyReader.unprocessedHexString());
            parserResult.setErrorMessage("PropertyResult must start with SD-ContextTag 0. Value is: " + sdContextTag0);
            parserResult.setParsedOk(false);
            throw new BacnetParserException("PropertyResult must start with SD-ContextTag 0. Value is: " + sdContextTag0, parserResult);
        }
        String objectIdHexString = propertyReader.next(4);
        ParserResult<ObjectId> objectIdResult = ObjectIdMapper.parse(objectIdHexString);
        if (!objectIdResult.isParsedOk()) {
            parserResult.setUnparsedHexString(propertyReader.unprocessedHexString());
            parserResult.setErrorMessage("Could not parse required parameter ObjectId from :" + objectIdHexString);
            parserResult.setParsedOk(false);
            throw new BacnetParserException("Could not parse required parameter ObjectId.", parserResult);
        } else {
            propertyResult.setObjectId(objectIdResult.getParsedObject());
        }

        //2 Read Property Identifier
        Octet sdContextTag1 = propertyReader.next();
        if (!sdContextTag1.equals(SDContextTag.TAG1LENGTH1)) {
            parserResult.setUnparsedHexString(propertyReader.unprocessedHexString());
            parserResult.setErrorMessage("PropertyResult must have  SD-ContextTag 1. Value is: " + sdContextTag1);
            parserResult.setParsedOk(false);
            throw new BacnetParserException("PropertyResult must have with SD-ContextTag 1. Value is: " + sdContextTag1, parserResult);
        }
        final Octet propertyIdentifierOctet = propertyReader.next();
        PropertyIdentifier propertyIdentifier = PropertyIdentifier.fromOctet(propertyIdentifierOctet);
        propertyResult.setPropertyIdentifier(propertyIdentifier);

        //3 Read Optional Property Array Index
        Octet sdContextTag = propertyReader.next();
        if (sdContextTag.getFirstNibble() == SDContextTag.TAG2LENGTH1.getFirstNibble()) {
            SDContextTag arrayIndexTag = new SDContextTag(sdContextTag);
            int numberofOctets = arrayIndexTag.findLength();
            String numberHex = propertyReader.next(numberofOctets);
            int arrayIndexNumber = toInt(numberHex);
            propertyResult.setArrayIndexNumber(arrayIndexNumber);
        }

        //4 Read Property Value
//        Octet readResultOctet = propertyReader.next();
        //property-value
        if (sdContextTag.equals(TAG3START)) {
            Octet applicationTagOctet = propertyReader.next();
            ApplicationTag applicationTag = new ApplicationTag(applicationTagOctet);
            Object value = null;
            int length = 0;
            ValueType valueType = applicationTag.findValueType();
            switch (valueType) {
                case BitString:
                    if (applicationTagOctet.getSecondNibble() == '5') {
                        //extended
                        length = toInt(propertyReader.next());
                        String bitStringHex = propertyReader.next(length);
                        String bitString = toBitString(bitStringHex);
                        propertyResult.setValue(bitString);
                        break;
                    }else {
                        parserResult.setUnparsedHexString(propertyReader.unprocessedHexString());
                        parserResult.setErrorMessage("Do not know how to Parse BitString with applicationOcetet of " + applicationTagOctet);
                        parserResult.setParsedOk(false);
                        throw new BacnetParserException("Do not know how to Parse BitString with applicationOcetet of " + applicationTagOctet, parserResult);
                    }

                case Float:
                    length = applicationTag.findLength();
                    value = toFloat(propertyReader.next(length));
                    propertyResult.setValue(value);
                    break;
                case Long:
                case Integer:
                    length = applicationTag.findLength();
                    value = toInt(propertyReader.nextOctets(length));
                    propertyResult.setValue(value);
                    break;
                case ObjectIdentifier:
                    objectIdHexString = propertyReader.next(4);
                    ParserResult<ObjectId> idResult = ObjectIdMapper.parse(objectIdHexString);
                    ObjectId propertyObjectId = idResult.getParsedObject();
                    propertyResult.setValue(propertyObjectId);
                    break;
                case CharString:
                    if (applicationTagOctet.getSecondNibble() == '5') {
                        ParserResult<String> stringResult = parseCharStringExtended(propertyReader.unprocessedHexString());
                        String text = stringResult.getParsedObject();
                        propertyResult.setValue(text);
                        propertyReader.next(stringResult.getNumberOfOctetsRead());
                    }
                    break;
                case Enumerated:
                    length = applicationTag.findLength();
                    Octet measurementUnitOctet = propertyReader.next();
                    MeasurementUnit measurementUnit = MeasurementUnit.fromOctet(measurementUnitOctet);
                    propertyResult.setValue(measurementUnit);
                    break;
                default:
                    throw new IllegalArgumentException("Not implemented yet, " + valueType);
            }
        }

        parserResult.setParsedObject(propertyResult);
        return parserResult;

    }
}

