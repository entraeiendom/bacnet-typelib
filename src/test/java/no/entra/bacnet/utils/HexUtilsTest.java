package no.entra.bacnet.utils;

import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.octet.OctetConstants;
import org.junit.jupiter.api.Test;

import static no.entra.bacnet.utils.HexUtils.intToHexString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HexUtilsTest {

    @Test
    void intToHexStringTest() {
        assertEquals("0018", intToHexString(24,4));
    }

    @Test
    void extendedValueUtf8() {
        Octet encoding = OctetConstants.ENCODING_UTF_8;
        String hexString = "4657464355";
        String value = HexUtils.parseExtendedValue(encoding, hexString);
        assertEquals("FWFCU", value);
    }

    @Test
    void extendedValueOldUSEncoding() {
        Octet encoding = OctetConstants.ENCODING_UCS_2;
        String objectNameHexString = "0053004f004b005000310036002d004e004100450034002f004600430042002e003400330034005f003100300031002d0031004f0055003000300031002e00520054003000300031";
        String value = HexUtils.parseExtendedValue(encoding,objectNameHexString);
        assertEquals("SOKP16-NAE4/FCB.434_101-1OU001.RT001", value);
    }
}