package no.entra.bacnet.internal.bvlc;

import no.entra.bacnet.bvlc.Bvlc;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.bvlc.Bvlc.BVLC_OCTET_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BvlcTest {

    @Test
    void toHexString() {
        String expected = "810a0017";
        Bvlc bvlc = new Bvlc(BvlcFunction.OriginalUnicastNpdu,23);
        assertEquals(expected, bvlc.toHexString());
    }

    @Test
    void equalsTest() {
        Bvlc bvlc = new Bvlc(BvlcFunction.OriginalUnicastNpdu,23);
        Bvlc equals = new Bvlc(BvlcFunction.OriginalUnicastNpdu,23);
        assertEquals(equals, bvlc);
    }

    @Test
    void totalOctetLength() {
        int npduHexStringLength = 12;
        int apduHexStringLength = 30;
        int numberOfOctets = apduHexStringLength/2 + npduHexStringLength/2 + BVLC_OCTET_LENGTH;
        Bvlc bvlc = new BvlcBuilder(BvlcFunction.OriginalUnicastNpdu).withTotalNumberOfOctets(numberOfOctets).build();
        assertEquals(25,bvlc.getFullMessageLength());
        assertEquals("810a0019", bvlc.toHexString());
    }
}