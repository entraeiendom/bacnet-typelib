package no.entra.bacnet.internal.services;

import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.internal.properties.ReadObjectPropertiesResult;
import no.entra.bacnet.internal.properties.ReadObjectPropertiesResultParser;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.properties.ReadPropertyMultipleResponse;
import no.entra.bacnet.properties.ReadPropertyMultipleService;
import no.entra.bacnet.services.*;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ServiceParser {
    private static final Logger log = getLogger(ServiceParser.class);

    public static ParserResult<Service> parse(ServiceChoice serviceChoice, String hexString) {
        ParserResult<Service> parserResult = null;

        if (serviceChoice == null) {
            parserResult = new ParserResult<>();
            parserResult.setInitialHexString(hexString);
            parserResult.setParsedOk(false);
            parserResult.setErrorMessage("Select type of service the code should parse to. ServiceChoice parameter is null");
        } else if (serviceChoice instanceof UnconfirmedServiceChoice) {
            parserResult = parseServiceNotExpectingReply((UnconfirmedServiceChoice) serviceChoice, hexString);
        } else if (serviceChoice instanceof ConfirmedServiceChoice) {
            parserResult = parseServiceExpectingReply((ConfirmedServiceChoice) serviceChoice, hexString);
        }

        return parserResult;
    }

    /*
    WhoIs, IAm, IHave, +++
     */
    protected static ParserResult<Service> parseServiceNotExpectingReply(UnconfirmedServiceChoice serviceChoice, String hexString) {
        ParserResult<Service> parserResult = new ParserResult<>();
        parserResult.setInitialHexString(hexString);

        try {
            switch (serviceChoice) {
                case IAm:
                    ParserResult<IAmService> iAmResult = IAmServiceParser.parse(hexString);
                    if (iAmResult.isParsedOk()) {
                        Service iamService = iAmResult.getParsedObject();
                        parserResult.setParsedObject(iamService);
                        parserResult.setParsedOk(true);
                        parserResult.setUnparsedHexString(iAmResult.getUnparsedHexString());
                    }
                    break;
                case WhoIs:
                    ParserResult<WhoIsService> whoIsResult = WhoIsServiceParser.parse(hexString);
                    if (whoIsResult.isParsedOk()) {
                        Service whoIsService = whoIsResult.getParsedObject();
                        parserResult.setParsedObject(whoIsService);
                        parserResult.setParsedOk(true);
                        parserResult.setUnparsedHexString(whoIsResult.getUnparsedHexString());
                    }
                    break;
                default:
                    //TODO

            }
        } catch (BacnetParserException e) {
            parserResult.setUnparsedHexString(hexString);
            parserResult.setErrorMessage(e.getMessage());
            parserResult.setParsedOk(false);
        }

        return parserResult;
    }

    /*
    ReadPropertiesMultiple +++
     */
    protected static ParserResult<Service> parseServiceExpectingReply(ConfirmedServiceChoice serviceChoice, String hexString) {
        ParserResult<Service> parserResult = new ParserResult<>();
        parserResult.setInitialHexString(hexString);
        try {
            switch (serviceChoice) {

                case ReadPropertyMultiple:
                    ParserResult<ReadObjectPropertiesResult> readPropertyMultipleResult = ReadObjectPropertiesResultParser.parse(hexString);
                    if (readPropertyMultipleResult.isParsedOk()) {
                        ReadObjectPropertiesResult objectPropertiesResult = readPropertyMultipleResult.getParsedObject();
                        ObjectId objectId = objectPropertiesResult.getObjectId();
                        ReadPropertyMultipleService readPropertyMultipleService = new ReadPropertyMultipleService();
                        readPropertyMultipleService.setObjectId(objectId);
                        ReadPropertyMultipleResponse response = new ReadPropertyMultipleResponse();
                        response.addReadObjectPropertiesResult(objectPropertiesResult);
                        readPropertyMultipleService.setReadPropertyMultipleResponse(response);
                        parserResult.setParsedObject(readPropertyMultipleService);
                        parserResult.setParsedOk(true);
                    }
                    break;
                default:
                    parserResult.setParsedOk(false);
                    parserResult.setErrorMessage("ServiceChoice: " + serviceChoice + " is not implemented.");
            }
        } catch (BacnetParserException e) {
            e.printStackTrace();
        }
        return parserResult;
    }
}
