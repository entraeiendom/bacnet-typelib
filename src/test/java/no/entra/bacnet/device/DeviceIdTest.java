package no.entra.bacnet.device;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceIdTest {

    @Test
    void equals() {
        DeviceId device1 = new DeviceId(1);
        DeviceId device1too = new DeviceId(1);
        assertEquals(device1, device1too);
        assertTrue(device1.equals(device1too));
        assertEquals(device1.hashCode(), device1too.hashCode());
    }
}