package no.entra.bacnet.bvlc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BvlcTest {

    @Test
    void toHexString() {
        String expected = "810a0017";
        Bvlc bvlc = new Bvlc(BvlcFunction.OriginalUnicastNpdu,23);
        assertEquals(expected, bvlc.toHexString());
    }
}