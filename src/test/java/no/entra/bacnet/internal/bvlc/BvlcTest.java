package no.entra.bacnet.internal.bvlc;

import no.entra.bacnet.bvlc.Bvlc;
import org.junit.jupiter.api.Test;

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
}