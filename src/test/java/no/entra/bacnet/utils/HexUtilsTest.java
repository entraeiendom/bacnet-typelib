package no.entra.bacnet.utils;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.utils.HexUtils.intToHexString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HexUtilsTest {

    @Test
    void intToHexStringTest() {
        assertEquals("0018", intToHexString(24,4));
    }
}