package no.entra.bacnet.internal.bvlc;

import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.octet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BvlcBuilderTest {

    @Test
    void build() {
        Octet[] messageLength = {Octet.fromHexString("00"), Octet.fromHexString("18")};
        Bvlc bvlc = new BvlcBuilder(BvlcFunction.ForwardedNpdu).withMessageLength(messageLength).build();
        assertNotNull(bvlc);
        assertEquals(BvlcFunction.ForwardedNpdu, bvlc.getFunction());
        assertEquals(24, bvlc.getFullMessageLength());
    }

    @Test
    void withNumericMessageLength() {
        Bvlc bvlc = new BvlcBuilder(BvlcFunction.ForwardedNpdu).withTotalNumberOfOctets(24).build();
        assertNotNull(bvlc);
        assertEquals(BvlcFunction.ForwardedNpdu, bvlc.getFunction());
        assertEquals(24, bvlc.getFullMessageLength());
    }
}