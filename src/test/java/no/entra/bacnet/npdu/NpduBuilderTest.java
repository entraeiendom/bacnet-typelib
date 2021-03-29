package no.entra.bacnet.npdu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NpduBuilderTest {

    @Test
    void withExpectingReply() {
        String expectedHexString = "0104";
        Npdu expectingReply = new NpduBuilder().withExpectingReply().build();
        assertEquals(expectedHexString, expectingReply.getHexString());
    }
}