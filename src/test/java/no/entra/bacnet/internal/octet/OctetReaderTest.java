package no.entra.bacnet.internal.octet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OctetReaderTest {

    private OctetReader octetReader;

    @BeforeEach
    void setUp() {
        octetReader = new OctetReader("af01a0");
    }

    @Test
    void next() {
        assertEquals("af", octetReader.next().toString());
        assertEquals("01", octetReader.next().toString());
        assertEquals("a0", octetReader.next().toString());
        assertNull(octetReader.next());
    }

    @Test
    void hasNext() {
        octetReader = new OctetReader("af");
        assertTrue(octetReader.hasNext());
        octetReader.next();
        assertFalse(octetReader.hasNext());
    }

    @Test
    void getManyNext() {
        octetReader = new OctetReader("112233445566");
        octetReader.next();
        assertEquals("223344", octetReader.next(3));
        // 4 octets are read, only two are left
        assertThrows(IllegalStateException.class, () -> {
            octetReader.next(3);
        });
    }
    @Test
    void getManyToTheEnd() {
        octetReader = new OctetReader("112233445566");
        assertEquals("112233445566", octetReader.next(6));
    }


    @Test
    void getCurrentPos() {
        octetReader.next();
        assertEquals(2, octetReader.getCurrentPos());
    }

    @Test
    void unprocessedHexStringTest() {
        String expected = "01a0";
        octetReader.next();
        assertEquals(expected, octetReader.unprocessedHexString());
    }

    @Test
    void bugFix() {
        String hexString = "810b000c0120ffff00ff1008";
        octetReader = new OctetReader(hexString);
        assertNotNull(octetReader);
    }
}