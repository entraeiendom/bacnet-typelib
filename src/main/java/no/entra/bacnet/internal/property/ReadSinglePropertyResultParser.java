package no.entra.bacnet.internal.property;

import no.entra.bacnet.internal.apdu.SDContextTag;
import no.entra.bacnet.internal.objects.ObjectIdMapper;
import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.internal.properties.PropertyIdentifier;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.services.BacnetParserException;
import org.slf4j.Logger;

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
        parserResult.setParsedObject(propertyResult);
        return parserResult;

    }
}

