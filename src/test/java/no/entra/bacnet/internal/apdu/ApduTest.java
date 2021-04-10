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

}