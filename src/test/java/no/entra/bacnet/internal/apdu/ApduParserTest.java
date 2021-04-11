package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.internal.parseandmap.ParserResult;
import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.services.BacnetParserException;
import no.entra.bacnet.services.ConfirmedServiceChoice;
import no.entra.bacnet.services.UnconfirmedServiceChoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApduParserTest {

    @Test
    void iamService() throws BacnetParserException {
        String iamApdu = "1000c40200020f22040091002105";
        ParserResult<Apdu> parserResult = ApduParser.parse(iamApdu);
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.UnconfirmedRequest, apdu.getMessageType());
        assertEquals(UnconfirmedServiceChoice.IAm, apdu.getServiceChoice());
        assertEquals("",parserResult.getUnparsedHexString());
    }

    /*
    @Test
    void readPropertiesObjectNameProtocolVersionRevision() throws BacnetParserException {
        String apduHexString = "30010e0c020000081e294d4e75060046574643554f29624e21014f298b4e210e4f1f";
        ParserResult<Apdu> parserResult = ApduParser.parse(apduHexString);
        Apdu apdu = parserResult.getParsedObject();
        assertNotNull(apdu);
        assertEquals(MessageType.ComplexAck, apdu.getMessageType());
        assertEquals(1, apdu.getInvokeId());
        assertEquals(ConfirmedServiceChoice.ReadPropertyMultiple, apdu.getServiceChoice());
        assertEquals("0c020000081e294d4e75060046574643554f29624e21014f298b4e210e4f1f",parserResult.getUnparsedHexString());
    }

     */

    @Test
    void findServiceChoice() {
        assertEquals(UnconfirmedServiceChoice.IAm, ApduParser.findServiceChoice(MessageType.UnconfirmedRequest, Octet.fromHexString("00")));
        assertEquals(ConfirmedServiceChoice.AcknowledgeAlarm, ApduParser.findServiceChoice(MessageType.ConfirmedRequest, Octet.fromHexString("00")));
        assertNull(ApduParser.findServiceChoice(MessageType.SegmentACK, Octet.fromHexString("00")));
        assertThrows(IllegalArgumentException.class, () -> ApduParser.findServiceChoice(null, Octet.fromHexString("00")));
    }

    @Test
    void expectServiceChoiceOctet() {

        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.Error));
        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.ComplexAck));
        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.ConfirmedRequest));
        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.SimpleAck));
        assertTrue(ApduParser.expectServiceChoiceOctet(MessageType.UnconfirmedRequest));
        assertFalse(ApduParser.expectServiceChoiceOctet(MessageType.Abort));
        assertFalse(ApduParser.expectServiceChoiceOctet(MessageType.Reject));
        assertFalse(ApduParser.expectServiceChoiceOctet(MessageType.SegmentACK));
    }
}