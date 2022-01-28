package no.entra.bacnet.internal.npdu;

import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.octet.Octet;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.utils.HexUtils.octetFromInt;
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

    @Test
    void withDestinationSpecified() {
        String expectedHexString = "0120ffff00ff";
        Octet[] networkAddress = {new Octet("ff"),new Octet("ff")};
        Npdu  npdu = new NpduBuilder(null)
                .setDestinationSpecifierControlBit(true)
                .withDestinationNetworkAddress(networkAddress)
                .withDestinationMacLayerAddress(new Octet("00"))
                .withHopCount(octetFromInt(255))
                .build();
        assertEquals(expectedHexString, npdu.toHexString());
    }
}