package no.entra.bacnet.internal.apdu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApduParserTest {

    @Test
    void parse() {
    }

    @Test
    void findServiceChoice() {
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