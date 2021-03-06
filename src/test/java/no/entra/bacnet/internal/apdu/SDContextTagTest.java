package no.entra.bacnet.internal.apdu;

import no.entra.bacnet.octet.Octet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SDContextTagTest {

    @Test
    void findEnumeration() {
        SDContextTag tag = new SDContextTag(Octet.fromHexString("09"));
        assertEquals(0, tag.findEnumeration());
        tag = new SDContextTag(Octet.fromHexString("19"));
        assertEquals(1, tag.findEnumeration());
        tag = new SDContextTag(Octet.fromHexString("29"));
        assertEquals(2, tag.findEnumeration());
        tag = new SDContextTag(Octet.fromHexString("39"));
        assertEquals(3, tag.findEnumeration());
    }

    @Test
    void findLength() {
        SDContextTag tag = new SDContextTag(Octet.fromHexString("09"));
        assertEquals(1, tag.findLength());
        tag = new SDContextTag(Octet.fromHexString("0a"));
        assertEquals(2, tag.findLength());
        tag = new SDContextTag(Octet.fromHexString("0b"));
        assertEquals(3, tag.findLength());
        tag = new SDContextTag(Octet.fromHexString("0c"));
        assertEquals(4, tag.findLength());
    }

    @Test
    void equalsTest() {
        assertEquals(new Octet("09"), SDContextTag.TAG0LENGTH1);
    }
}