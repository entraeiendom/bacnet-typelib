package no.entra.bacnet.octet;

import no.entra.bacnet.apdu.SDContextTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OctetTest {

    @Test
    void testEquals() {
        Octet octet29 = new Octet("29");
        Octet octet29too = new Octet("29");
        assertEquals(octet29, octet29too);
        assertEquals(octet29, SDContextTag.TAG2LENGTH1);
    }
}