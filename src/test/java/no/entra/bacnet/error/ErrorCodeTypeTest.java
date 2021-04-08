package no.entra.bacnet.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorCodeTypeTest {

    @Test
    void toStringTest() {
        assertEquals("UnknownProperty", ErrorCodeType.UnknownProperty.toString());
    }
}