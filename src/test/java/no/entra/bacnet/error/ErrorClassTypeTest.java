package no.entra.bacnet.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorClassTypeTest {

    @Test
    void toStringTest() {
        assertEquals("device", ErrorClassType.device.toString());
    }
}