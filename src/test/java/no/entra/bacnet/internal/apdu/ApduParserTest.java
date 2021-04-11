package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.services.ConfirmedServiceChoice;
import no.entra.bacnet.services.UnconfirmedServiceChoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApduParserTest {

    @Test
    void parse() {
    }

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