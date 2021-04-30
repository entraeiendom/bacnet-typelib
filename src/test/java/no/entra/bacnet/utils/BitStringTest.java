package no.entra.bacnet.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitStringTest {

    private BitString bitString;

    @BeforeEach
    void setUp() {
        bitString = new BitString(4);
    }

    @Test
    void test4() {
        bitString.setBit(3);
        assertEquals("4", bitString.toHexString());
        assertEquals('4', bitString.toChar());
        assertEquals("0100", bitString.toString());

    }

    @Test
    void test15() {
        bitString = new BitString(16);
        bitString.setBit(1);
        bitString.setBit(2);
        bitString.setBit(3);
        bitString.setBit(4);
        assertEquals("0000000000001111", bitString.toString());
        assertEquals("f", bitString.toHexString());
        assertEquals('f', bitString.toChar());

    }

    @Test
    void test16() {
        bitString = new BitString(16);
        bitString.setBit(5);
        assertEquals(16, bitString.toInt());
        assertEquals("10", bitString.toHexString());
        assertThrows(IllegalStateException.class, () -> bitString.toChar());
        assertEquals("0000000000010000", bitString.toString());
    }

    @Test
    void equalsTest() {
        bitString = new BitString(16);
        bitString.setBit(5);
        BitString bitString2 = new BitString(16);
        bitString2.setBit(5);
        assertEquals(bitString, bitString2);
        bitString2.unsetBit(5);
        assertNotEquals(bitString, bitString2);
    }
}