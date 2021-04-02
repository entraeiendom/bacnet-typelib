package no.entra.bacnet.utils;

import no.entra.bacnet.octet.Octet;
import no.entra.bacnet.octet.OctetConstants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static no.entra.bacnet.utils.HexUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void toIntTest() {
        assertThrows(IllegalArgumentException.class, () -> toInt(""));
        String isnull = null;
        assertThrows(IllegalArgumentException.class, () -> toInt(isnull));
        assertEquals(12, toInt("0c"));
    }

    @Test
    public void findMessageLengthTest() {
        String expectedContent = "810a002a01040005020109121c020003e92c0080000139004e09552e44400000002f096f2e8204002f4f";
        String bacnetMessageAsHex = expectedContent + "00000000000000000000000000000000000";
        assertEquals(expectedContent.length(), findMessageLength(bacnetMessageAsHex));
    }

    @Test
    void hexStringToByteArrayTest() {
        String hexString = "810b000c0120ffff00ff1008";
        byte[] bytesAsIntArr = hexStringToByteArray(hexString);
        byte[] expectedArray = new byte[] {-127, 11, 0, 12, 1, 32, -1, -1, 0, -1, 16, 8};
        assertTrue(Arrays.equals(expectedArray, bytesAsIntArr));
    }
}