package no.entra.bacnet.internal.npdu;

import no.entra.bacnet.npdu.Npdu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NpduBuilderTest {

    @Test
    void withExpectingReply() {
        String expectedHexString = "0104";
        Npdu expectingReply = new NpduBuilder().withExpectingReply().build();
        assertEquals(expectedHexString, expectingReply.toHexString());
    }

    @Test
    void normalMessage() {
        String expectedHexString = "0100";
        Npdu normalMessage = new NpduBuilder().build();
        assertEquals(expectedHexString, normalMessage.toHexString());
        normalMessage = new NpduBuilder().withNormalMessage().build();
        assertEquals(expectedHexString, normalMessage.toHexString());
    }
}