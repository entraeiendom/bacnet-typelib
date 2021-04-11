package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.internal.octet.OctetReader;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.services.BacnetParserException;
import no.entra.bacnet.services.ConfirmedServiceChoice;
import no.entra.bacnet.services.ServiceChoice;
import no.entra.bacnet.services.UnconfirmedServiceChoice;
import no.entra.bacnet.utils.HexUtils;
import org.slf4j.Logger;

import static no.entra.bacnet.internal.apdu.PduFlags.*;
import static no.entra.bacnet.utils.HexUtils.toInt;
import static org.slf4j.LoggerFactory.getLogger;

public class ApduParser {
    private static final Logger log = getLogger(ApduParser.class);

    public static ParserResult<Apdu> parse(String hexString) throws BacnetParserException {
        //FIXME copy code from no.entra.bacnet.json.services.ServiceParser
        ParserResult<Apdu> parserResult = new ParserResult<>();
        Apdu apdu = null;
        ServiceChoice serviceChoice = null;
//        Service service = null;
        Octet serviceChoiceOctet = null;
        OctetReader serviceReader = new OctetReader(hexString);
        Octet messageTypeOctet = serviceReader.next();
        log.debug("PDU Type {} in bits: {}{}", messageTypeOctet, HexUtils.toBitString(messageTypeOctet.getFirstNibble()),HexUtils.toBitString(messageTypeOctet.getSecondNibble()));
        MessageType messageType = MessageType.fromOctet(messageTypeOctet);
        apdu = new Apdu(messageType);
        parserResult.setParsedObject(apdu);
        if (messageType != null) {
            char pduFlags = messageTypeOctet.getSecondNibble();

            if (isSegmented(pduFlags)) {
                apdu.setSegmented(true);
            }
            if (hasMoreSegments(pduFlags)) {
                apdu.setHasMoreSegments(true);
            }
            try {
                if (willAcceptSegmentedResponse(pduFlags)) {
                    apdu.setSegmentedReplyAllowed(true);
                    Octet maxApduOctetsAccepted = serviceReader.next();
                    apdu.setMaxApduLengthAccepted(maxApduOctetsAccepted.getSecondNibble());


                    Octet invokeIdOctet = serviceReader.next();
                    int invokeId = toInt(invokeIdOctet);
                    apdu.setInvokeId(invokeId);

                    if (expectServiceChoiceOctet(messageType)) {
                        serviceChoiceOctet = serviceReader.next();
                        //Could be ConfirmedServiceChoice, or UnconfirmedServiceChoice
                        serviceChoice = findServiceChoice(messageType, serviceChoiceOctet);
                        apdu.setServiceChoice(serviceChoice);
                    }
                }
            } catch (IllegalArgumentException e) {
                throw new BacnetParserException(e.getMessage(), parserResult);
            }
            //apdu.setServiceChoice;
        }
        return null;
    }

    static ServiceChoice findServiceChoice(MessageType messageType, Octet serviceChoiceOctet) {
        ServiceChoice serviceChoice = null;
        switch (messageType) {
            case Error:
            case SimpleAck:
            case ComplexAck:
            case ConfirmedRequest:
                serviceChoice = ConfirmedServiceChoice.fromOctet(serviceChoiceOctet);
                break;
            case UnconfirmedRequest:
                serviceChoice = UnconfirmedServiceChoice.fromOctet(serviceChoiceOctet);
                break;
            case Reject:
            case Abort:
            case SegmentACK:
                serviceChoice = null;
                break;
            default:
                throw new IllegalArgumentException("MessageType: " + " is not expected. Do not know how to parse this message.");
        }
        return serviceChoice;
    }

    static boolean expectServiceChoiceOctet(MessageType messageType) {
        boolean hasServiceChoice = true;
        switch (messageType) {
            case Abort:
            case Reject:
            case SegmentACK:
                hasServiceChoice = false;
                break;
            default:
                hasServiceChoice = true;

        }
        return hasServiceChoice;
    }
}
