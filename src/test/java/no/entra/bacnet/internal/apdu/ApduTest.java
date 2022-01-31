package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.services.ConfirmedServiceChoice;
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

    }

    @Test
    void equalsTest() {
        Apdu apdu = Apdu.ApduBuilder.builder()
                .withApduType(MessageType.ConfirmedRequest)
                .isSegmented(false)
                .hasMoreSegments(false)
                .isSegmentedReplyAllowed(true)
                .withMaxSegmentsAcceptedAbove64()
                .withMaxApduLength1476()
                .build();
        Apdu apdu2 = Apdu.ApduBuilder.builder()
                .withApduType(MessageType.ConfirmedRequest)
                .isSegmented(false)
                .hasMoreSegments(false)
                .isSegmentedReplyAllowed(true)
                .withMaxSegmentsAcceptedAbove64()
                .withMaxApduLength1476()
                .build();
        assertEquals(apdu, apdu2);
    }

    @Test
    void subscribeCovTest() {
        String expectedHex = "00020f05";
        Apdu apdu = Apdu.ApduBuilder.builder()
                .withApduType(MessageType.ConfirmedRequest)
                .isSegmented(false)
                .hasMoreSegments(false)
                .isSegmentedReplyAllowed(false)
                .withMaxSegmentsAccepted('0')
                .withMaxApduLength206()
                .withInvokeId(15)
                .withServiceChoice(ConfirmedServiceChoice.SubscribeCov)
                .build();
        assertEquals(expectedHex,apdu.toHexString());

    }

    @Test
    void ackSubscribeCovTest() {
        Apdu apdu = Apdu.ApduBuilder.builder()
                .withApduType(MessageType.SimpleAck)
                .withInvokeId(15)
                .withServiceChoice(ConfirmedServiceChoice.ConfirmedCovNotificationMultiple)
                .build();
        String expectedHex = "200f1f";
        assertEquals(expectedHex,apdu.toHexString());
    }
}