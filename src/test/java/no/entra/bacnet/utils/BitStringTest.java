package no.entra.bacnet.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals("f", bitString.toHexString());
        assertEquals('f', bitString.toChar());
        assertEquals("0000000000001111", bitString.toString());
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
}