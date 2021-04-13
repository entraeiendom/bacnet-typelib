package no.entra.bacnet.device;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
        Map<DeviceId, String> map = new HashMap<>();
        map.put(device1, "Device1");
        map.put(device1too, "DeviceToo");
        assertEquals(1, map.size());
        assertEquals("DeviceToo", map.get(device1));
    }
}