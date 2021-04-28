package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.apdu.Apdu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApduTest {

    @Test
    void toHexString() {
        Apdu apdu = Apdu.ApduBuilder.builder()
                .withApduType(MessageType.ConfirmedRequest)
                .isSegmented(false)
                .hasMoreSegments(false)
                .isSegmentedReplyAllowed(true)
                .withMaxSegmentsAcceptedAbove64()
                .withMaxApduLength1476()
                .build();
        String expectedHexString = "0275";
        assertEquals(expectedHexString, apdu.toHexString());

    }

    @Test
    void segmentAck() {
        Apdu apdu = Apdu.ApduBuilder.builder()
                .withApduType(MessageType.SegmentACK)
                .withIsNegativeAck(false)
                .withSenderIsServer(false)
                .build();
        String expectedHexString = "40";
        assertEquals(expectedHexString, apdu.toHexString());
        /*
        BACnet-SegmentACK-PDU ::= SEQUENCE {
pdu-type
[0] Unsigned (0..15), -- 4 for this PDU type
reserved
[1] Unsigned (0..3), -- shall be set to zero
negative-ack
[2] BOOLEAN,
server
[3] BOOLEAN,
original-invoke-id
[4] Unsigned (0..255),
sequence-number
[5] Unsigned (0..255),
actual-window-size
[6] Unsigned (1..127)
-- Context-spec
         */
    }
}